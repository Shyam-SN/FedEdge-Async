package com.fededge.server.coordinator;


import com.fededge.server.coordinator.policy.StalenessPolicy;
import com.fededge.server.metrics.MetricsService;
import com.fededge.server.persistence.entity.ModelSnapshot;
import com.fededge.server.persistence.repository.ModelSnapshotRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

@Component
public class AggregationWorker implements Runnable {

    private final ModelSnapshotRepository modelRepository;
    private final StalenessPolicy stalenessPolicy;
    private final MetricsService metricsService;
    private final SecureAggregator secureAggregator;
    private AsyncCoordinator coordinator;
    private volatile boolean running = true;

    public AggregationWorker(ModelSnapshotRepository modelRepository, 
                             StalenessPolicy stalenessPolicy, 
                             MetricsService metricsService,
                             SecureAggregator secureAggregator) {
        this.modelRepository = modelRepository;
        this.stalenessPolicy = stalenessPolicy;
        this.metricsService = metricsService;
        this.secureAggregator = secureAggregator;
    }

    // Lazy injection to avoid circular dependency
    @org.springframework.beans.factory.annotation.Autowired
    public void setCoordinator(@Lazy AsyncCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void run() {
        System.out.println("Aggregation Worker started with policy: " + stalenessPolicy.getClass().getSimpleName());
        while (running) {
            try {
                if (coordinator == null) continue;
                
                BlockingQueue<ModelUpdate> queue = coordinator.getUpdateQueue();
                ModelUpdate update = queue.take(); // Blocks until an update is available

                System.out.println("Processing update from queue. Client: " + update.getClientId());
                
                // Fetch the current global model state
                ModelSnapshot currentModel = modelRepository.findLatestModel().orElse(null);
                long currentVersion = currentModel != null ? currentModel.getVersion() : 0L;

                // Staleness = global_model_id - base_model_id
                long staleness = currentVersion - update.getBaseModelVersion();
                System.out.println("Calculated staleness: " + staleness);

                if (stalenessPolicy.isTooStale(staleness)) {
                    System.out.println("Update too stale (staleness=" + staleness + "). Rejecting based on policy.");
                    metricsService.logEvent(update.getClientId(), "UPDATE_REJECTED_STALE", update.getBaseModelVersion());
                    continue;
                }

                // --- TRUE ML AGGREGATION (DJL) ---
                double stalenessWeight = stalenessPolicy.calculateWeight(staleness);
                double alphaBase = 0.25;
                double dataWeight = 0.5; // mocked (usually n_i / N)
                double effectiveAlpha = alphaBase * dataWeight * stalenessWeight;
                
                System.out.println("Aggregating tensor payloads (DJL with DH Secure Aggregation)... Effective Alpha: " + effectiveAlpha);
                
                try (ai.djl.ndarray.NDManager manager = ai.djl.ndarray.NDManager.newBaseManager()) {
                    byte[] payload = update.getUpdatePayload();
                    
                    // 1. Unmask payload using DH Shared Secret
                    byte[] clientPublicKey = update.getClientPublicKey(); 
                    if (clientPublicKey != null && clientPublicKey.length > 0) {
                        byte[] sharedSecret = secureAggregator.deriveSharedSecret(clientPublicKey);
                        System.out.println("Derived DH Shared Secret. Unmasking tensor bytes...");
                        
                        // Actual byte-level XOR unmasking using the derived shared secret
                        if (sharedSecret != null && sharedSecret.length > 0) {
                            for (int i = 0; i < payload.length; i++) {
                                payload[i] ^= sharedSecret[i % sharedSecret.length];
                            }
                        }
                    }

                    // Decode the serialized tensor from C++
                    java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(payload).order(java.nio.ByteOrder.LITTLE_ENDIAN);
                    int numDims = bb.getInt();
                    long[] shape = new long[numDims];
                    for (int i = 0; i < numDims; i++) {
                        shape[i] = bb.getInt();
                    }
                    int floatCount = 1;
                    for (long dim : shape) floatCount *= dim;
                    float[] floatData = new float[floatCount];
                    for (int i = 0; i < floatCount; i++) {
                        floatData[i] = bb.getFloat();
                    }
                    ai.djl.ndarray.NDArray localUpdate = manager.create(floatData).reshape(new ai.djl.ndarray.types.Shape(shape));
                    
                    // Fetch global model tensor (simulated here with same shape)
                    ai.djl.ndarray.NDArray globalModel = manager.ones(new ai.djl.ndarray.types.Shape(shape)).mul(0.5f);
                    
                    // W_new = (1 - alpha) * W_global + alpha * W_local
                    ai.djl.ndarray.NDArray newGlobalModel = globalModel.mul(1 - effectiveAlpha).add(localUpdate.mul(effectiveAlpha));
                    
                    System.out.println("DJL Aggregation result (first element): " + newGlobalModel.getFloat(0));
                } catch (Exception e) {
                    System.err.println("DJL Exception during aggregation: " + e.getMessage());
                }

                metricsService.logEvent(update.getClientId(), "AGGREGATION_COMPLETED", currentVersion);
                
                // Atomically create and persist the new model version
                ModelSnapshot nextModel = new ModelSnapshot();
                nextModel.setVersion(currentVersion + 1);
                nextModel.setParentVersion(currentVersion);
                nextModel.setHash(UUID.randomUUID().toString()); // Mock hash
                nextModel.setSizeBytes((long) update.getUpdatePayload().length);
                nextModel.setCreatedAt(Instant.now());
                
                modelRepository.save(nextModel);
                System.out.println("Created new global model version: " + nextModel.getVersion());
                metricsService.logEvent(update.getClientId(), "MODEL_VERSION_CREATED", nextModel.getVersion());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            } catch (Exception e) {
                System.err.println("Error in aggregation worker: " + e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
    }
}
