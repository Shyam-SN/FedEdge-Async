package com.fededge.server.coordinator.policy;

import org.springframework.stereotype.Component;

@Component
public class ReciprocalDecayPolicy implements StalenessPolicy {
    
    private final double beta = 0.5;
    private final long maxStaleness = 10;

    @Override
    public double calculateWeight(long staleness) {
        if (staleness <= 0) return 1.0;
        return 1.0 / (1.0 + beta * staleness);
    }

    @Override
    public boolean isTooStale(long staleness) {
        return staleness > maxStaleness;
    }
}
