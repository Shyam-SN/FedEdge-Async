#pragma once

#include <vector>
#include <cstdint>
#include <iostream>

namespace fededge {

class Tensor {
public:
    Tensor(const std::vector<int>& shape) : shape_(shape) {
        size_t total_size = 1;
        for (int dim : shape) {
            total_size *= dim;
        }
        data_.resize(total_size, 0.0f);
    }

    const std::vector<int>& shape() const { return shape_; }
    float* data() { return data_.data(); }
    const float* data() const { return data_.data(); }
    size_t size() const { return data_.size(); }
    size_t byte_size() const { return data_.size() * sizeof(float); }

    // Phase 3 requirement preview: delta calculation
    Tensor calculate_delta(const Tensor& base_model) const {
        Tensor delta(shape_);
        for (size_t i = 0; i < data_.size(); ++i) {
            delta.data_[i] = data_[i] - base_model.data_[i];
        }
        return delta;
    }

    // Phase 3: Tensor serialization format
    std::vector<uint8_t> serialize() const {
        std::vector<uint8_t> buffer;
        
        // 1. Write number of dimensions
        int32_t num_dims = shape_.size();
        auto* num_dims_ptr = reinterpret_cast<const uint8_t*>(&num_dims);
        buffer.insert(buffer.end(), num_dims_ptr, num_dims_ptr + sizeof(int32_t));
        
        // 2. Write shape metadata
        for (int dim : shape_) {
            int32_t d = dim;
            auto* dim_ptr = reinterpret_cast<const uint8_t*>(&d);
            buffer.insert(buffer.end(), dim_ptr, dim_ptr + sizeof(int32_t));
        }
        
        // 3. Write float32 data
        auto* data_ptr = reinterpret_cast<const uint8_t*>(data_.data());
        buffer.insert(buffer.end(), data_ptr, data_ptr + byte_size());
        
        return buffer;
    }

private:
    std::vector<int> shape_;
    std::vector<float> data_;
};

} // namespace fededge
