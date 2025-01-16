package com.fededge.server.coordinator;

public class ModelUpdate {

    private final String clientId;
    private final long baseModelVersion;
    private final byte[] updatePayload;
    private final byte[] clientPublicKey;

    public ModelUpdate(String clientId, long baseModelVersion, byte[] updatePayload) {
        this(clientId, baseModelVersion, updatePayload, new byte[0]);
    }

    public ModelUpdate(String clientId, long baseModelVersion, byte[] updatePayload, byte[] clientPublicKey) {
        this.clientId = clientId;
        this.baseModelVersion = baseModelVersion;
        this.updatePayload = updatePayload;
        this.clientPublicKey = clientPublicKey;
    }

    public String getClientId() {
        return clientId;
    }

    public long getBaseModelVersion() {
        return baseModelVersion;
    }

    public byte[] getUpdatePayload() {
        return updatePayload;
    }

    public byte[] getClientPublicKey() {
        return clientPublicKey;
    }

    // Legacy method for Validator compilation
    public int getProtocolVersion() { return 1; }
    public String getModelHash() { return ""; }
}
