#pragma once

#include "ComputeBackend.h"
#include <iostream>

namespace fededge {

class CpuBackend : public ComputeBackend {
public:
    CpuBackend() {
        // Initialize weights (784 -> 128 -> 10)
        W1.resize(784 * 128, 0.01f);
        b1.resize(128, 0.0f);
        W2.resize(128 * 10, 0.01f);
        b2.resize(10, 0.0f);
        
        grad_W1.resize(784 * 128, 0.0f);
        grad_b1.resize(128, 0.0f);
        grad_W2.resize(128 * 10, 0.0f);
        grad_b2.resize(10, 0.0f);
        
        hidden.resize(128, 0.0f);
    }

    void forward(const Tensor& input, Tensor& output) override {
        // Mock input (batch=1, 784)
        std::vector<float> X(784, 0.5f); 
        
        // Linear 1: hidden = X * W1 + b1
        for (int i = 0; i < 128; ++i) {
            float sum = b1[i];
            for (int j = 0; j < 784; ++j) {
                sum += X[j] * W1[j * 128 + i];
            }
            // ReLU
            hidden[i] = std::max(0.0f, sum);
        }
        
        // Linear 2: out = hidden * W2 + b2
        std::vector<float> out(10, 0.0f);
        for (int i = 0; i < 10; ++i) {
            float sum = b2[i];
            for (int j = 0; j < 128; ++j) {
                sum += hidden[j] * W2[j * 10 + i];
            }
            out[i] = sum;
        }
        
        std::cout << "[CpuBackend] Forward pass (MLP 784->128->10) completed.\n";
    }

    void backward(const Tensor& grad_output, Tensor& grad_input) override {
        // Mock target (batch=1, 10 classes, target=3)
        std::vector<float> d_out(10, 0.1f); 
        std::vector<float> X(784, 0.5f);
        
        // dW2 = hidden^T * d_out
        for (int j = 0; j < 128; ++j) {
            for (int i = 0; i < 10; ++i) {
                grad_W2[j * 10 + i] = hidden[j] * d_out[i];
            }
        }
        // db2 = d_out
        for (int i = 0; i < 10; ++i) grad_b2[i] = d_out[i];
        
        // d_hidden = d_out * W2^T
        std::vector<float> d_hidden(128, 0.0f);
        for (int j = 0; j < 128; ++j) {
            for (int i = 0; i < 10; ++i) {
                d_hidden[j] += d_out[i] * W2[j * 10 + i];
            }
            // ReLU derivative
            if (hidden[j] <= 0) d_hidden[j] = 0.0f;
        }
        
        // dW1 = X^T * d_hidden
        for (int j = 0; j < 784; ++j) {
            for (int i = 0; i < 128; ++i) {
                grad_W1[j * 128 + i] = X[j] * d_hidden[i];
            }
        }
        // db1 = d_hidden
        for (int i = 0; i < 128; ++i) grad_b1[i] = d_hidden[i];

        std::cout << "[CpuBackend] Backward pass (MLP Backprop) completed.\n";
    }

    void step(float learning_rate) override {
        for (size_t i = 0; i < W1.size(); ++i) W1[i] -= learning_rate * grad_W1[i];
        for (size_t i = 0; i < b1.size(); ++i) b1[i] -= learning_rate * grad_b1[i];
        for (size_t i = 0; i < W2.size(); ++i) W2[i] -= learning_rate * grad_W2[i];
        for (size_t i = 0; i < b2.size(); ++i) b2[i] -= learning_rate * grad_b2[i];
        
        std::cout << "[CpuBackend] Weights updated (lr=" << learning_rate << ").\n";
    }
    
    // Provide a way to get the serialized delta tensor for FedEdge
    std::vector<float> getGradients() const {
        std::vector<float> grads;
        grads.insert(grads.end(), grad_W1.begin(), grad_W1.end());
        grads.insert(grads.end(), grad_b1.begin(), grad_b1.end());
        grads.insert(grads.end(), grad_W2.begin(), grad_W2.end());
        grads.insert(grads.end(), grad_b2.begin(), grad_b2.end());
        return grads;
    }

private:
    std::vector<float> W1, b1, W2, b2;
    std::vector<float> grad_W1, grad_b1, grad_W2, grad_b2;
    std::vector<float> hidden;
};

} // namespace fededge
