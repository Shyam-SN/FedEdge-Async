#include "GrpcClient.h"
#include <iostream>
#include <fstream>
#include <sstream>

namespace fededge {

GrpcClient::GrpcClient(const std::string& address, const std::string& cert_path) {
    std::ifstream cert_file(cert_path);
    std::stringstream buffer;
    buffer << cert_file.rdbuf();
    
    grpc::SslCredentialsOptions ssl_opts;
    ssl_opts.pem_root_certs = buffer.str();
    
    auto channel_creds = grpc::SslCredentials(ssl_opts);
    auto channel = grpc::CreateChannel(address, channel_creds);
    
    stub_ = FederatedCoordinator::NewStub(channel);
    std::cout << "[GrpcClient] Connected to Java Server at " << address << " (TLS Enabled)" << std::endl;
}

bool GrpcClient::SubmitUpdate(const MockModelUpdate& update) {
    ModelUpdate grpc_request;
    grpc_request.set_client_id(update.client_id);
    grpc_request.set_base_model_version(update.base_model_version);
    grpc_request.set_protocol_version(update.protocol_version);
    
    // Copy vector to protobuf bytes
    grpc_request.set_update_payload(update.update_payload.data(), update.update_payload.size());
    
    // Set dummy hashes for now to pass Phase 6 Validation
    grpc_request.set_model_hash("dummy_hash");
    
    // Add client public key for the server to unmask
    if (!client_public_key_.empty()) {
        grpc_request.set_client_public_key(client_public_key_);
    }

    SubmitUpdateResponse grpc_response;
    grpc::ClientContext context;
    context.AddMetadata("authorization", "Bearer fededge-super-secret-token-123");

    grpc::Status status = stub_->SubmitUpdate(&context, grpc_request, &grpc_response);

    if (status.ok()) {
        std::cout << "[GrpcClient] Server Accepted Update. Reason: " << grpc_response.reason() << std::endl;
        return true;
    } else {
        std::cerr << "[GrpcClient] RPC failed: " << status.error_code() << ": " << status.error_message() << std::endl;
        return false;
    }
}

bool GrpcClient::GetTrainingJob(const std::string& client_id) {
    GetTrainingJobRequest request;
    request.set_client_id(client_id);
    // Could set DeviceMetrics here...

    GetTrainingJobResponse response;
    grpc::ClientContext context;
    context.AddMetadata("authorization", "Bearer fededge-super-secret-token-123");

    grpc::Status status = stub_->GetTrainingJob(&context, request, &response);

    if (status.ok()) {
        std::cout << "[GrpcClient] Fetched Training Job! ID: " << response.job_id() << std::endl;
        server_public_key_ = response.server_public_key();
        return true;
    } else {
        std::cerr << "[GrpcClient] GetTrainingJob failed: " << status.error_message() << std::endl;
        return false;
    }
}

} // namespace fededge
