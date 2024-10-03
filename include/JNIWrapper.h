#pragma once

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

// Example JNI methods mapped to com.fededge.engine.NativeTrainer

// Initializes the C++ engine (Trainer + Backend) and returns a handle pointer
JNIEXPORT jlong JNICALL Java_com_fededge_engine_NativeTrainer_nativeInit(JNIEnv* env, jobject thiz, jstring backend_type);

// Runs the training loop given the handle
JNIEXPORT jfloat JNICALL Java_com_fededge_engine_NativeTrainer_nativeTrain(JNIEnv* env, jobject thiz, jlong handle, jint epochs, jint steps);

// Calculates the delta and returns a serialized byte array
JNIEXPORT jbyteArray JNICALL Java_com_fededge_engine_NativeTrainer_nativeGetUpdatePayload(JNIEnv* env, jobject thiz, jlong handle);

// Cleans up the C++ engine
JNIEXPORT void JNICALL Java_com_fededge_engine_NativeTrainer_nativeDestroy(JNIEnv* env, jobject thiz, jlong handle);

#ifdef __cplusplus
}
#endif
