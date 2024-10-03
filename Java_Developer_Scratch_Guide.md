# FedEdge-Async: Full Stack Developer Guide (From Scratch)

This guide provides a structural roadmap for a Java Developer tasked with rebuilding the **FedEdge-Async** ecosystem from scratch. 
Instead of copy-pasting exact code, this document outlines the directory architecture and explains *what* logic needs to be implemented in each file. You will use IntelliJ/Eclipse for the Spring Boot Server, and Android Studio for the Mobile Application.

---

## Part 1: The Java Spring Boot Server
This module acts as the central Orchestrator for Federated Learning.

### Directory Structure
```text
server/
├── build.gradle
├── src/
│   ├── main/
│   │   ├── proto/
│   │   │   └── fededge.proto
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── java/
│   │       └── com/
│   │           └── fededge/
│   │               └── server/
│   │                   ├── FedEdgeServerApplication.java
│   │                   ├── coordinator/
│   │                   │   ├── AggregationWorker.java
│   │                   │   └── TrainingScheduler.java
│   │                   ├── grpc/
│   │                   │   ├── FederatedGrpcService.java
│   │                   │   └── JwtAuthInterceptor.java
│   │                   └── security/
│   │                       └── SecureAggregator.java
```

### Implementation Guidelines
1. **`build.gradle`**: Set up a Spring Boot project. Include dependencies for gRPC (`grpc-server-spring-boot-starter`), Protobuf, and DJL (`ai.djl:api`) for tensor math. Configure the `protobuf` block to auto-generate the gRPC stubs.
2. **`fededge.proto`**: Define the gRPC contract. You need two RPC endpoints: `GetTrainingJob` and `SubmitUpdate`. Ensure you include fields for the Diffie-Hellman public keys, model hashes, and byte arrays for the tensor payload.
3. **`application.properties`**: Enable gRPC on port 9090. Enable `grpc.server.security.enabled` and point it to a `server.crt` and `server.key` for TLS.
4. **`FedEdgeServerApplication.java`**: The standard Spring Boot entry point (`@SpringBootApplication`).
5. **`TrainingScheduler.java`**: Implement logic to assign training epochs and batch sizes to specific `client_ids` connecting to the network. 
6. **`SecureAggregator.java`**: Use Java `KeyPairGenerator` ("EC") to generate an Elliptic Curve key pair on boot. Write a method that takes a client's public key bytes and uses `KeyAgreement` to derive a shared Diffie-Hellman secret.
7. **`AggregationWorker.java`**: Create a background thread with a `BlockingQueue`. When an update arrives, pop it, unmask the bytes via XOR using the DH shared secret, and use the DJL `NDManager` to mathematically merge it with the global model using an exponential moving average (EMA) formula.
8. **`JwtAuthInterceptor.java`**: Implement `ServerInterceptor`. Check the gRPC `Metadata` for the `authorization` header. If the Bearer token is invalid, reject the call.
9. **`FederatedGrpcService.java`**: Extend the generated `FederatedCoordinatorImplBase`. Handle the two RPC endpoints by dispatching logic to the `TrainingScheduler` and `AggregationWorker`.

---

## Part 2: The Android Mobile Application
This module acts as the Edge node that actually trains the AI.

### Directory Structure
```text
android-app/
├── build.gradle
├── app/
│   ├── build.gradle
│   ├── CMakeLists.txt
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── cpp/
│       │   │   └── (Your C++ Native Core Files)
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── fededge/
│       │   │           ├── app/
│       │   │           │   └── MainActivity.java
│       │   │           └── engine/
│       │   │               └── NativeTrainer.java
│       │   └── res/
│       │       └── layout/
│       │           └── activity_main.xml
```

### Implementation Guidelines
1. **`AndroidManifest.xml`**: Ensure you request `INTERNET` and `ACCESS_NETWORK_STATE` permissions to communicate with the Spring Boot server.
2. **`build.gradle` (App Level)**: Enable `externalNativeBuild` to point to your `CMakeLists.txt`. Include gRPC-OkHttp dependencies for Android.
3. **`CMakeLists.txt`**: Write the NDK build script to compile the pure C++ Math Engine (MLP Forward/Backward) and the JNI Wrapper into an `.so` shared library. Link against `log-lib`.
4. **`NativeTrainer.java`**: Write the JNI Bridge. Load the `fededge_engine` library and declare `native` methods for initialization, training an epoch, fetching the update payload, and memory destruction.
5. **`activity_main.xml`**: Design a simple UI using Android Studio's layout editor. Include a status text view and a "Start Training" button.
6. **`MainActivity.java`**: Bind the UI elements. On button click, spawn a background executor thread, instantiate `NativeTrainer`, call the native training loops, extract the payload, and send it to the Java Server over gRPC.

---

## Part 3: The React Dashboard
This module acts as the visualization interface for administrators.

### Directory Structure
```text
dashboard/
├── package.json
├── public/
│   └── index.html
├── src/
│   ├── index.js
│   ├── App.js
│   ├── App.css
│   └── components/
│       ├── MetricsChart.js
│       └── NetworkTopology.js
```

### Implementation Guidelines
1. **`package.json`**: Initialize a standard React or Next.js app. Include dependencies like `recharts` for graphing and `framer-motion` for animations.
2. **`App.js`**: Create the main layout. Use modern CSS (glassmorphism, dark mode) to create a premium feel. Set up a polling mechanism (or WebSockets) to fetch real-time loss metrics from the Java Server.
3. **`MetricsChart.js`**: Implement a line chart that plots the Global Model Loss over time as new federated updates are aggregated.
4. **`NetworkTopology.js`**: Create a visual representation of active Edge devices (Android phones) connected to the central Spring Boot server. Update the state when new devices execute the `GetTrainingJob` RPC.

---
*End of Guide*
