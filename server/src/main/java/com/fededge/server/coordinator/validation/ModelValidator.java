package com.fededge.server.coordinator.validation;

import com.fededge.grpc.ModelUpdate;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

@Component
public class ModelValidator {
    
    // Simple mock to track duplicates: client_id + base_model_version
    private final Set<String> seenUpdates = ConcurrentHashMap.newKeySet();

    public boolean validate(ModelUpdate update) {
        String updateKey = update.getClientId() + "-" + update.getBaseModelVersion();
        
        if (seenUpdates.contains(updateKey)) {
            System.err.println("Duplicate update detected for key: " + updateKey);
            return false;
        }

        // Validate payload integrity / hash
        if (update.getUpdatePayload().isEmpty()) {
            System.err.println("Empty payload detected for client: " + update.getClientId());
            return false;
        }

        // Check model hash (mock)
        if (update.getModelHash().isEmpty()) {
            System.err.println("Missing model hash from client: " + update.getClientId());
            return false;
        }

        seenUpdates.add(updateKey);
        return true;
    }
}
