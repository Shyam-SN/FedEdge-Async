# FedEdge-Async ⚡

**Asynchronous Federated Learning System for Edge Devices**

FedEdge-Async is a high-performance, asynchronous Federated Learning infrastructure designed for the edge. Unlike standard synchronous Federated Learning (like traditional FedAvg) which suffers from the "straggler effect" (waiting for the slowest device to finish), FedEdge-Async allows individual edge devices to pull the latest model, train locally, and asynchronously submit their updates. 

This repository contains the complete end-to-end system: an Asynchronous Java Spring Boot Server Coordinator, a hyper-optimized C++ Desktop Client, and an Android JNI Mobile Client.

---

## 🏗️ System Architecture

### 1. Java Spring Boot Coordinator (Server)
- **Asynchronous Aggregation Engine**: Accepts incoming model deltas asynchronously via gRPC. 
- **Time-Weighted Averaging**: Applies a dampening factor to "stale" updates (updates computed on older model versions) so that late stragglers do not corrupt the global model convergence.
- **Deep Java Library (DJL)**: Uses DJL under the hood to perform raw, high-performance tensor math operations on the Java backend.

### 2. C++ Desktop Core (Mac/Linux Client)
- A highly optimized native client that communicates with the server via gRPC and Protocol Buffers.
- Simulates local epochs of training, computes deltas, and streams them back to the server securely via TLS.

### 3. Android Mobile Application (Edge Client)
- Contains a native JNI (Java Native Interface) bridge (`libfededge_engine.so`) mapping directly to the C++ Core.
- Allows Android devices to run the ultra-fast C++ Tensor training natively on the CPU/Metal backend while bypassing Java's performance overhead.
- Includes a mock network implementation to bypass complex Android NDK gRPC compilation, demonstrating UI integration and background JNI threading.

---

## 🚀 Setup & Installation

### 1. Running the Java Server
The server coordinates the learning process and must be started first.
```bash
cd server

# Using Gradle wrapper to build and boot the Spring application
./gradlew bootRun
```
*The server will start listening for gRPC connections on port `9090`.*

### 2. Running the C++ Desktop Client (Mac/Linux)
You will need CMake, OpenSSL, gRPC, and Protobuf installed on your system.

```bash
# Clone the repository
git clone git@github.com:Shyam-SN/FedEdge-Async.git
cd FedEdge-Async

# Create a build directory
mkdir build && cd build

# Compile the native client
cmake ..
make -j$(nproc)

# Run the client
./FedEdgeAsync
```
*You will see the desktop client connect to the Java server, perform simulated training epochs, and asynchronously upload gradients.*

### 3. Running the Android Application
To run the Android app, you will need Android Studio and the Android NDK installed.

1. Open Android Studio.
2. Select **Open an existing Android Studio project**.
3. Navigate to and select the `FedEdge-Async/android-app` folder.
4. Let Gradle sync and build the C++ native libraries via CMake.
5. Connect your Android device via ADB and click **Run**.

---

## 🔒 Privacy & Security
FedEdge-Async implements edge-side Privacy Processors before any data is transmitted:
- **L2 Norm Gradient Clipping**: Ensures no single update can overwhelm the global model.
- **Local Differential Privacy (DP)**: Injects Gaussian noise directly into the computed deltas to guarantee cryptographic anonymity for edge data.
