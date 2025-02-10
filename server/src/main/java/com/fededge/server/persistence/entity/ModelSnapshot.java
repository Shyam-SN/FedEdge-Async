package com.fededge.server.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "models")
public class ModelSnapshot {
    @Id
    private Long version;
    private Long parentVersion;
    private String hash;
    private Long sizeBytes;
    private Instant createdAt;
    
    // We can also store the file path to the model payload blob on disk/S3
    private String storagePath;

    // Getters and Setters
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public Long getParentVersion() { return parentVersion; }
    public void setParentVersion(Long parentVersion) { this.parentVersion = parentVersion; }

    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }

    public Long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getStoragePath() { return storagePath; }
    public void setStoragePath(String storagePath) { this.storagePath = storagePath; }
}
