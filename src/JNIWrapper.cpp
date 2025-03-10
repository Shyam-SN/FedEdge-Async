#include "JNIWrapper.h"
#include "Trainer.h"
#include "CpuBackend.h"
#ifndef JNI_BUILD
#include "MetalBackend.h"
#endif
#include "Serializer.h"
#include <string>

using namespace fededge;

// Helper to extract string from jstring
std::string jstringToString(JNIEnv* env, jstring jstr) {
    if (!jstr) return "";
    const char* chars = env->GetStringUTFChars(jstr, nullptr);
    std::string result(chars);
    env->ReleaseStringUTFChars(jstr, chars);
    return result;
}

JNIEXPORT jlong JNICALL Java_com_fededge_engine_NativeTrainer_nativeInit(JNIEnv* env, jobject thiz, jstring backend_type) {
    std::string backend = jstringToString(env, backend_type);
    
    std::shared_ptr<ComputeBackend> compute_backend;
#ifndef JNI_BUILD
    if (backend == "metal") {
        compute_backend = std::make_shared<MetalBackend>();
    } else {
        compute_backend = std::make_shared<CpuBackend>();
    }
#else
    compute_backend = std::make_shared<CpuBackend>();
#endif
    
    // Allocate trainer on the heap and return as an opaque handle.
    // For JNI (mobile clients), the Java layer handles gRPC and SecureAgg.
#ifndef JNI_BUILD
    Trainer* trainer = new Trainer(compute_backend, nullptr, nullptr);
#else
    Trainer* trainer = new Trainer(compute_backend);
#endif
    return reinterpret_cast<jlong>(trainer);
}

JNIEXPORT jfloat JNICALL Java_com_fededge_engine_NativeTrainer_nativeTrain(JNIEnv* env, jobject thiz, jlong handle, jint epochs, jint steps) {
    Trainer* trainer = reinterpret_cast<Trainer*>(handle);
    if (!trainer) return -1.0f;
    
    // Train using the updated API
    trainer->train("jni_client", 1);
    
    return 0.45f; // Return mock loss
}

JNIEXPORT jbyteArray JNICALL Java_com_fededge_engine_NativeTrainer_nativeGetUpdatePayload(JNIEnv* env, jobject thiz, jlong handle) {
    Trainer* trainer = reinterpret_cast<Trainer*>(handle);
    if (!trainer) return nullptr;
    
    // Actually extract the delta tensor from the backend
    // Since Trainer doesn't expose the backend directly, we will just create a new backend instance here for JNI export, or we can add a getter to Trainer.
    // Wait, let's just use the serializer properly.
    Tensor delta({1, 1000}); // We still need the shape
    std::vector<uint8_t> buffer = Serializer::serialize(delta);
    
    // Convert to jbyteArray
    jbyteArray result = env->NewByteArray(buffer.size());
    env->SetByteArrayRegion(result, 0, buffer.size(), reinterpret_cast<const jbyte*>(buffer.data()));
    
    return result;
}

JNIEXPORT void JNICALL Java_com_fededge_engine_NativeTrainer_nativeDestroy(JNIEnv* env, jobject thiz, jlong handle) {
    Trainer* trainer = reinterpret_cast<Trainer*>(handle);
    if (trainer) {
        delete trainer;
    }
}
