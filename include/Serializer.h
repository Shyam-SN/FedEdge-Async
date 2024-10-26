#pragma once
#include "Tensor.h"
#include <vector>
#include <cstdint>

namespace fededge {
class Serializer {
public:
    static std::vector<uint8_t> serialize(const Tensor& tensor) {
        // Mock serialization
        return std::vector<uint8_t>(tensor.size() * sizeof(float), 0);
    }
};
}
