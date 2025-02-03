package com.fededge.server.metrics;

import com.fededge.server.persistence.entity.TrainingEvent;
import com.fededge.server.persistence.repository.TrainingEventRepository;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
    
    private final TrainingEventRepository eventRepository;

    public MetricsService(TrainingEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void logEvent(String clientId, String eventType, Long modelVersion) {
        logEvent(clientId, eventType, modelVersion, null, null);
    }

    public void logEvent(String clientId, String eventType, Long modelVersion, Double loss, Double accuracy) {
        TrainingEvent event = new TrainingEvent(clientId, eventType, modelVersion, loss, accuracy);
        eventRepository.save(event);
        System.out.println("[MetricsService] Logged Event: " + eventType + " | Client: " + clientId);
    }
}
