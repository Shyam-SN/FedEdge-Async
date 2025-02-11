package com.fededge.server.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "training_events")
public class TrainingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String clientId;
    private String eventType;
    private Instant timestamp;
    private Long modelVersion;
    private Double loss;
    private Double accuracy;

    public TrainingEvent() {}

    public TrainingEvent(String clientId, String eventType, Long modelVersion, Double loss, Double accuracy) {
        this.clientId = clientId;
        this.eventType = eventType;
        this.timestamp = Instant.now();
        this.modelVersion = modelVersion;
        this.loss = loss;
        this.accuracy = accuracy;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public Long getModelVersion() { return modelVersion; }
    public void setModelVersion(Long modelVersion) { this.modelVersion = modelVersion; }

    public Double getLoss() { return loss; }
    public void setLoss(Double loss) { this.loss = loss; }

    public Double getAccuracy() { return accuracy; }
    public void setAccuracy(Double accuracy) { this.accuracy = accuracy; }
}
