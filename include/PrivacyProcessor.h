#pragma once
#include "Tensor.h"

namespace fededge {
class PrivacyProcessor {
public:
    PrivacyProcessor(float clipping_norm, float noise_multiplier) {}

    void apply_local_privacy(Tensor& tensor) {
        // Mock DP
    }
};
}
