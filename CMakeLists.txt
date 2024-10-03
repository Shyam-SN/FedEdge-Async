cmake_minimum_required(VERSION 3.15)
project(FedEdge-Async VERSION 0.1.0 LANGUAGES CXX OBJCXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(OpenSSL REQUIRED)
find_package(gRPC CONFIG REQUIRED)
find_package(Protobuf REQUIRED)

# Protobuf/gRPC generation
set(PROTO_SRC "${CMAKE_CURRENT_SOURCE_DIR}/server/src/main/proto/fededge.proto")
set(PROTO_GEN_DIR "${CMAKE_CURRENT_BINARY_DIR}/generated")
file(MAKE_DIRECTORY ${PROTO_GEN_DIR})

add_custom_command(
    OUTPUT "${PROTO_GEN_DIR}/fededge.pb.cc"
           "${PROTO_GEN_DIR}/fededge.pb.h"
           "${PROTO_GEN_DIR}/fededge.grpc.pb.cc"
           "${PROTO_GEN_DIR}/fededge.grpc.pb.h"
    COMMAND protobuf::protoc
    ARGS --grpc_out="${PROTO_GEN_DIR}"
         --cpp_out="${PROTO_GEN_DIR}"
         --plugin=protoc-gen-grpc=$<TARGET_FILE:gRPC::grpc_cpp_plugin>
         -I "${CMAKE_CURRENT_SOURCE_DIR}/server/src/main/proto"
         "${PROTO_SRC}"
    DEPENDS "${PROTO_SRC}"
)

add_library(fededge_grpc_proto
    "${PROTO_GEN_DIR}/fededge.pb.cc"
    "${PROTO_GEN_DIR}/fededge.grpc.pb.cc"
)
target_link_libraries(fededge_grpc_proto PUBLIC gRPC::grpc++ protobuf::libprotobuf)
target_include_directories(fededge_grpc_proto PUBLIC "${PROTO_GEN_DIR}")

add_executable(FedEdgeAsync 
    src/main.mm
    src/GrpcClient.cpp
    src/SecureAggregator.cpp
)

target_include_directories(FedEdgeAsync PRIVATE include)
target_link_libraries(FedEdgeAsync PRIVATE fededge_grpc_proto OpenSSL::SSL OpenSSL::Crypto)

# Add Metal frameworks for macOS
if(APPLE)
    find_library(METAL_LIBRARY Metal)
    find_library(MPS_LIBRARY MetalPerformanceShaders)
    find_library(FOUNDATION_LIBRARY Foundation)
    find_library(COREFOUNDATION_LIBRARY CoreFoundation)
    target_link_libraries(FedEdgeAsync PRIVATE 
        ${METAL_LIBRARY} 
        ${MPS_LIBRARY}
        ${FOUNDATION_LIBRARY} 
        ${COREFOUNDATION_LIBRARY}
    )
endif()

# JNI Shared Library for Android
find_package(JNI)
if(JNI_FOUND OR ANDROID)
    add_library(fededge_jni SHARED src/JNIWrapper.cpp)
    target_include_directories(fededge_jni PRIVATE include)
    if(JNI_FOUND)
        target_include_directories(fededge_jni PRIVATE ${JNI_INCLUDE_DIRS})
    endif()
    # On Android NDK, jni.h is automatically available in the sysroot.
endif()
