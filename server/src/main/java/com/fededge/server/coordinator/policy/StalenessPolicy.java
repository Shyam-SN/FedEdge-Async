package com.fededge.server.coordinator.policy;

public interface StalenessPolicy {
    /**
     * Calculates the staleness weight factor for a given update.
     * @param staleness The difference between the global model version and the client's base model version.
     * @return The weight multiplier (e.g. 1.0 for no penalty, < 1.0 for penalization).
     */
    double calculateWeight(long staleness);
    
    /**
     * Checks if the update is too stale to be aggregated at all.
     * @param staleness The staleness of the update.
     * @return true if it should be rejected/quarantined.
     */
    boolean isTooStale(long staleness);
}
