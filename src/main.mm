#include "CpuBackend.h"
#include "MetalBackend.h"
#include "Trainer.h"
#include "Tensor.h"
#include "GrpcClient.h"
#include "SecureAggregator.h"
#include <memory>
#include <iostream>
#include <string>
#include <thread>
#include <chrono>
#include <vector>


using namespace fededge;

int main(int argc, char** argv) {
    std::cout << "Starting FedEdge-Async C++ Native Core (Real Network Integration)...\n";
    
    // 1. Initialize Compute Backend
    std::shared_ptr<ComputeBackend> backend = std::make_shared<CpuBackend>();
    
    // 2. Setup gRPC Client
    std::string cert_path = "../server/src/main/resources/server.crt";
    std::shared_ptr<GrpcClient> grpc_client = std::make_shared<GrpcClient>("localhost:9090", cert_path);
    
    // 3. Fetch Training Job and DH Public Key
    std::string client_id = "macbook_pro_m2_001";
    bool job_granted = grpc_client->GetTrainingJob(client_id);
    if (!job_granted) {
        std::cerr << "Training job denied. Exiting.\n";
        return 1;
    }

    // 4. Initialize DH Keypair
    std::shared_ptr<SecureAggregator> dh_aggregator = std::make_shared<SecureAggregator>();
    if (!dh_aggregator->initialize()) {
        std::cerr << "Failed to init DH keys.\n";
        return 1;
    }
    
    // 5. Initialize Trainer
    Trainer trainer(backend, grpc_client, dh_aggregator);
    
    // 6. Simulate a local training session
    int64_t base_version = 1;

    std::cout << "\n[System] Starting Simulated Federated Training...\n";
    for (int epoch = 1; epoch <= 3; ++epoch) {
        std::cout << "\n--- EPOCH " << epoch << " ---\n";
        trainer.train(client_id, base_version);
        
        std::this_thread::sleep_for(std::chrono::milliseconds(500));
        base_version++; 
    }
    
    std::cout << "\n=== Local Training Session Complete ===\n";
    return 0;
}
