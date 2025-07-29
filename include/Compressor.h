#pragma once
#include <vector>
#include <cstdint>

namespace fededge {
class Compressor {
public:
    static std::vector<uint8_t> compress_fp32_to_int8(const std::vector<uint8_t>& data) {
        return data; // Passed through for simplicity
    }
};
}
