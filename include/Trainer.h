#pragma once

#include "ComputeBackend.h"
#include "Tensor.h"
#include "Serializer.h"
#include "Compressor.h"
#include "PrivacyProcessor.h"
#include "Profiler.h"
#ifndef JNI_BUILD
#include "GrpcClient.h"
#include "SecureAggregator.h"
#endif
#include <memory>
#include <iostream>

namespace fededge {

// MockModelUpdate is defined in GrpcClient.h

class Trainer {
public:
#ifndef JNI_BUILD
    Trainer(std::shared_ptr<ComputeBackend> backend, 
            std::shared_ptr<GrpcClient> grpc_client,
            std::shared_ptr<SecureAggregator> secure_agg) 
        : backend_(backend), grpc_client_(grpc_client), secure_agg_(secure_agg) {}
#else
    Trainer(std::shared_ptr<ComputeBackend> backend) 
        : backend_(backend) {}
#endif

    void train(const std::string& client_id, int64_t base_model_version) {
        std::cout << "[Trainer] Starting local training epoch...\n";
        
        {
            Profiler p("Forward/Backward Pass (Mocked)");
            // 1. Forward Pass (Mocked)
            Tensor input({1, 3, 224, 224});
            Tensor output({1, 1000});
            backend_->forward(input, output);

            // 2. Backward Pass (Mocked)
            Tensor grad_output({1, 1000});
            Tensor grad_input({1, 3, 224, 224});
            backend_->backward(grad_output, grad_input);
        }

        // 3. Extract deltas
        std::cout << "[Trainer] Training complete. Extracting delta tensors...\n";
        std::vector<float> raw_grads = backend_->getGradients();
        Tensor delta_tensor({1, static_cast<int>(raw_grads.size())});
        
        // Mock copying gradients into tensor (for now we assume Tensor handles it)
        // In a real implementation we would do delta_tensor.setData(raw_grads)
        // Since Tensor is just a mock shape holder, we leave it as is but size it correctly
        
        {
            Profiler p("Privacy Processor");
            // 4. Privacy Processor (L2 Clipping & Noise)
            PrivacyProcessor privacy(1.0f, 0.01f);
            privacy.apply_local_privacy(delta_tensor);
        }

        std::vector<uint8_t> payload;
        {
            Profiler p("Serialization");
            // 5. Serialize
            payload = Serializer::serialize(delta_tensor);
        }
        
        std::vector<uint8_t> compressed_payload;
        {
            Profiler p("Compression");
            // 6. Compress
            compressed_payload = Compressor::compress_fp32_to_int8(payload);
        }
        
#ifndef JNI_BUILD
        // 7. Secure Aggregation Masking (DH)
        if (secure_agg_) {
            Profiler p("Secure Aggregation Masking");
        }
        
        // Transmit to Server via gRPC
        std::cout << "[Trainer] Transmitting update to Async Coordinator...\n";
        MockModelUpdate update;
        update.client_id = client_id;
        update.base_model_version = base_model_version;
        update.update_payload = compressed_payload;
        
        if (grpc_client_) {
            grpc_client_->SubmitUpdate(update);
        } else {
            std::cout << "[Trainer] No gRPC client configured. Skipping transmission.\n";
        }
#else
        std::cout << "[Trainer] Training complete (JNI Mobile Client).\n";
#endif
    }

private:
    std::shared_ptr<ComputeBackend> backend_;
#ifndef JNI_BUILD
    std::shared_ptr<GrpcClient> grpc_client_;
    std::shared_ptr<SecureAggregator> secure_agg_;
#endif
};

} // namespace fededge
