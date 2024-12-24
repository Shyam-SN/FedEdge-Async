cmake_minimum_required(VERSION 3.22.1)
project(FedEdgeNative)

set(CMAKE_CXX_STANDARD 17)

# Specify the path to our native C++ core code
set(FEDEDGE_CORE_PATH "${CMAKE_CURRENT_SOURCE_DIR}/../../")

# Include directories
include_directories(
    ${FEDEDGE_CORE_PATH}/include
)

# Build our shared library
add_library(
    fededge_engine
    SHARED
    ${FEDEDGE_CORE_PATH}/src/JNIWrapper.cpp
)

# For a full build, we would also need to link OpenSSL, gRPC, and Protobuf here.
# Since building OpenSSL/gRPC for Android NDK is extremely complex, we will stub them
# out in the JNI build by using compiler flags, or we just rely on standard Android libs.
# For this demonstration, we will remove gRPC/OpenSSL from the JNI library
# and handle the networking + DH key exchange in Java on the Android side,
# leaving the heavy tensor math (CpuBackend, Serializer) in C++.

target_compile_definitions(fededge_engine PRIVATE JNI_BUILD=1)

# Link against Android libraries
find_library(log-lib log)
target_link_libraries(fededge_engine ${log-lib})
