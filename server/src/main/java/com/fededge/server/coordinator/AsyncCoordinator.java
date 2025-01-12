package com.fededge.server.coordinator;

import com.fededge.server.coordinator.validation.ModelValidator;
import com.fededge.server.metrics.MetricsService;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class AsyncCoordinator {

    private final BlockingQueue<com.fededge.server.coordinator.ModelUpdate> updateQueue = new LinkedBlockingQueue<>();
    private final AggregationWorker aggregationWorker;
    private final ModelValidator modelValidator;
    private final MetricsService metricsService;

    public AsyncCoordinator(AggregationWorker aggregationWorker, ModelValidator modelValidator, MetricsService metricsService) {
        this.aggregationWorker = aggregationWorker;
        this.modelValidator = modelValidator;
        this.metricsService = metricsService;
        // Start the single aggregation worker thread
        new Thread(this.aggregationWorker).start();
    }

    public boolean submitUpdate(com.fededge.server.coordinator.ModelUpdate update) {
        metricsService.logEvent(update.getClientId(), "UPDATE_SUBMITTED", update.getBaseModelVersion());

        // Queue the update
        boolean added = updateQueue.offer(update);
        if (!added) {
            System.err.println("Failed to queue update for client: " + update.getClientId());
            metricsService.logEvent(update.getClientId(), "UPDATE_REJECTED", update.getBaseModelVersion());
            return false;
        } else {
            metricsService.logEvent(update.getClientId(), "UPDATE_ACCEPTED", update.getBaseModelVersion());
            return true;
        }
    }

    public BlockingQueue<com.fededge.server.coordinator.ModelUpdate> getUpdateQueue() {
        return updateQueue;
    }

    public long getLatestModelVersion() {
        // Dummy implementation since we don't have the repository injected directly here
        return 1L;
    }
}
