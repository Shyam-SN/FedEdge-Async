#pragma once

#include "ComputeBackend.h"
#include <iostream>

namespace fededge {

struct MetalContext; // Forward declaration to hide Objective-C++ details

class MetalBackend : public ComputeBackend {
public:
    MetalBackend();
    ~MetalBackend() override;

    void forward(const Tensor& input, Tensor& output) override;
    void backward(const Tensor& grad_output, Tensor& grad_input) override;
    void step(float learning_rate) override;

private:
    MetalContext* context_;
};

} // namespace fededge
