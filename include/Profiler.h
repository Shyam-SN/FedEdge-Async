#pragma once

#include <chrono>
#include <iostream>
#include <string>

namespace fededge {

class Profiler {
public:
    Profiler(const std::string& name) : name_(name) {
        start_time_ = std::chrono::high_resolution_clock::now();
    }

    ~Profiler() {
        auto end_time = std::chrono::high_resolution_clock::now();
        auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end_time - start_time_).count();
        std::cout << "[Profiler] " << name_ << " took " << duration << " ms.\n";
    }

private:
    std::string name_;
    std::chrono::high_resolution_clock::time_point start_time_;
};

} // namespace fededge
