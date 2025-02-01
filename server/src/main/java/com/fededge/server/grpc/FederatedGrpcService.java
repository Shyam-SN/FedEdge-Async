package com.fededge.server.grpc;

import com.fededge.grpc.FederatedCoordinatorGrpc;
import com.fededge.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import com.google.protobuf.ByteString;

import java.util.UUID;
import com.fededge.server.coordinator.AsyncCoordinator;

@GrpcService
public class FederatedGrpcService extends FederatedCoordinatorGrpc.FederatedCoordinatorImplBase {

    private final AsyncCoordinator coordinator;
    private final com.fededge.server.coordinator.TrainingScheduler scheduler;
    private final com.fededge.server.coordinator.SecureAggregator secureAggregator;

    public FederatedGrpcService(AsyncCoordinator coordinator, 
                                com.fededge.server.coordinator.TrainingScheduler scheduler,
                                com.fededge.server.coordinator.SecureAggregator secureAggregator) {
        this.coordinator = coordinator;
        this.scheduler = scheduler;
        this.secureAggregator = secureAggregator;
    }

    @Override
    public void getTrainingJob(GetTrainingJobRequest request, StreamObserver<GetTrainingJobResponse> responseObserver) {
        StringBuilder rejectReason = new StringBuilder();
        boolean granted = scheduler.shouldGrantJob(request.getMetrics(), rejectReason);

        if (!granted) {
            System.out.println("[Scheduler] Denying job to client " + request.getClientId() + " due to: " + rejectReason.toString());
            // Return empty job ID to signify rejection
            GetTrainingJobResponse response = GetTrainingJobResponse.newBuilder()
                    .setJobId("")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        System.out.println("[Scheduler] Granting job to client " + request.getClientId() + " (Diffie-Hellman Key Exchange initiated)");
        
        // If client sent a public key, we could derive the secret here, but typically we just send ours back.
        // The actual secret derivation will happen during the Aggregation step when they submit the payload.
        
        GetTrainingJobResponse response = GetTrainingJobResponse.newBuilder()
                .setJobId(UUID.randomUUID().toString())
                .setGlobalModelVersion(coordinator.getLatestModelVersion())
                .setModelDownloadUrl("/api/model/download")
                .setServerPublicKey(ByteString.copyFrom(secureAggregator.getPublicKey()))
                .build();
                
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void registerClient(RegisterClientRequest request, StreamObserver<RegisterClientResponse> responseObserver) {
        // Phase 1: Simple registration
        System.out.println("Registering client. Type: " + request.getDeviceType());
        
        String clientId = UUID.randomUUID().toString();
        RegisterClientResponse response = RegisterClientResponse.newBuilder()
                .setClientId(clientId)
                .setProtocolVersion(1)
                .build();
                
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getGlobalModel(GetGlobalModelRequest request, StreamObserver<GetGlobalModelResponse> responseObserver) {
        // Phase 1: Return a mock global model for now
        System.out.println("Client requested global model: " + request.getClientId());
        
        GetGlobalModelResponse response = GetGlobalModelResponse.newBuilder()
                .setVersion(1L)
                .setHash("mock_hash_v1")
                .setModelPayload(ByteString.copyFrom(new byte[]{0x00, 0x01, 0x02})) // Mock payload
                .build();
                
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void submitUpdate(ModelUpdate request, StreamObserver<SubmitUpdateResponse> responseObserver) {
        System.out.println("[gRPC] Received real SubmitUpdate request from Client: " + request.getClientId());
        System.out.println("       Base Model Version: " + request.getBaseModelVersion());
        System.out.println("       Payload Size: " + request.getUpdatePayload().size() + " bytes");

        // Convert the gRPC protobuf bytes to standard byte array for the queue
        byte[] payloadBytes = request.getUpdatePayload().toByteArray();
        byte[] clientPublicKey = request.getClientPublicKey().toByteArray();
        
        System.out.println("       Converted to Java byte[] of length: " + payloadBytes.length);

        // Package into our internal queue format (Phase 3 format)
        com.fededge.server.coordinator.ModelUpdate internalUpdate = new com.fededge.server.coordinator.ModelUpdate(
                request.getClientId(),
                request.getBaseModelVersion(),
                payloadBytes,
                clientPublicKey
        );

        boolean accepted = coordinator.submitUpdate(internalUpdate);
        
        SubmitUpdateResponse response = SubmitUpdateResponse.newBuilder()
                .setAccepted(accepted)
                .setReason(accepted ? "Update queued for processing" : "Update rejected by validation or queue full")
                .build();
                
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    // Other methods (GetTrainingJob, Heartbeat) will be implemented later
}
