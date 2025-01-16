package com.fededge.engine;

public class NativeTrainer {
    static {
        System.loadLibrary("fededge_engine");
    }

    public native long nativeInit(String backendType);
    public native float nativeTrain(long handle, int epochs, int steps);
    public native byte[] nativeGetUpdatePayload(long handle);
    public native void nativeDestroy(long handle);

    public long initEngine(String backend) {
        return nativeInit(backend);
    }

    public float trainEpoch(long handle, int epochs, int steps) {
        return nativeTrain(handle, epochs, steps);
    }

    public byte[] getUpdatePayload(long handle) {
        return nativeGetUpdatePayload(handle);
    }

    public void destroy(long handle) {
        nativeDestroy(handle);
    }
}
