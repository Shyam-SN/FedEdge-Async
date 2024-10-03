#pragma once

#include <string>
#include <vector>
#include <memory>
#include <grpcpp/grpcpp.h>
#include "fededge.grpc.pb.h"

namespace fededge {

// A mock struct representing the update (to bridge with Trainer)
// In a real scenario we'd use the protobuf directly, but we map it here
struct MockModelUpdate {
    std::string client_id;
    int64_t base_model_version;
    std::vector<uint8_t> update_payload;
    int protocol_version = 1;
};

class GrpcClient {
public:
    GrpcClient(const std::string& address, const std::string& cert_path);
    bool SubmitUpdate(const MockModelUpdate& update);
    bool GetTrainingJob(const std::string& client_id);

private:
    std::unique_ptr<FederatedCoordinator::Stub> stub_;
    std::string server_public_key_;
    std::string client_public_key_;
    std::vector<uint8_t> dh_shared_secret_;
};

} // namespace fededge
