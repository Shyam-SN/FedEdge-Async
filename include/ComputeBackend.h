#pragma once

#include "Tensor.h"

namespace fededge {

class ComputeBackend {
public:
    virtual ~ComputeBackend() = default;

    virtual void forward(const Tensor& input, Tensor& output) = 0;
    virtual void backward(const Tensor& grad_output, Tensor& grad_input) = 0;
    
    // Perform weight updates
    virtual void step(float learning_rate) = 0;
    
    // Extract calculated gradients for federated transmission
    virtual std::vector<float> getGradients() const { return {}; }
};

} // namespace fededge
