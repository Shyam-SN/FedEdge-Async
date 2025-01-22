package com.fededge.server.coordinator.policy;

public class BoundedDecayPolicy implements StalenessPolicy {
    
    private final long maxStaleness = 10;

    @Override
    public double calculateWeight(long staleness) {
        if (staleness <= 0) return 1.0;
        if (staleness > maxStaleness) return 0.0;
        // Linear decay up to max staleness
        return 1.0 - ((double) staleness / maxStaleness);
    }

    @Override
    public boolean isTooStale(long staleness) {
        return staleness > maxStaleness;
    }
}
