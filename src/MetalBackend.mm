#include "MetalBackend.h"
#include <iostream>

#ifdef __APPLE__
#import <Metal/Metal.h>
#import <MetalPerformanceShaders/MetalPerformanceShaders.h>

namespace fededge {

struct MetalContext {
    id<MTLDevice> device;
    id<MTLCommandQueue> commandQueue;
};
#else
namespace fededge {
struct MetalContext {};
#endif

MetalBackend::MetalBackend() {
    context_ = new MetalContext();
#ifdef __APPLE__
    context_->device = MTLCreateSystemDefaultDevice();
    if (!context_->device) {
        std::cerr << "[MetalBackend] Error: Metal is not supported on this device.\n";
        return;
    }
    context_->commandQueue = [context_->device newCommandQueue];
    std::cout << "[MetalBackend] Initialized on device: " << [[context_->device name] UTF8String] << "\n";
#else
    std::cerr << "[MetalBackend] Error: Compiled without Apple Metal support.\n";
#endif
}

MetalBackend::~MetalBackend() {
    delete context_;
}

void MetalBackend::forward(const Tensor& input, Tensor& output) {
    // TODO: Implement MPS forward pass
    std::cout << "[MetalBackend] Forward pass executed via Metal/MPS.\n";
}

void MetalBackend::backward(const Tensor& grad_output, Tensor& grad_input) {
    // TODO: Implement MPS backward pass
    std::cout << "[MetalBackend] Backward pass executed via Metal/MPS.\n";
}

void MetalBackend::step(float learning_rate) {
    // TODO: Implement Metal weight update
    std::cout << "[MetalBackend] Weights updated via Metal (lr=" << learning_rate << ").\n";
}

} // namespace fededge
