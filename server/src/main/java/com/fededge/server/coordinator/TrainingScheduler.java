package com.fededge.server.coordinator;

import com.fededge.grpc.DeviceMetrics;
import org.springframework.stereotype.Service;

@Service
public class TrainingScheduler {

    // Thresholds
    private static final float MIN_BATTERY = 20.0f;
    private static final String THERMAL_SEVERE = "SEVERE";
    private static final String THERMAL_CRITICAL = "CRITICAL";

    /**
     * Determines whether a client should be given a training job based on its reported device metrics.
     * @param metrics The metrics from the client.
     * @param rejectReason A StringBuilder to hold the reason if rejected.
     * @return true if the job is granted, false otherwise.
     */
    public boolean shouldGrantJob(DeviceMetrics metrics, StringBuilder rejectReason) {
        if (metrics == null) {
            return true; // If no metrics provided (e.g. MacBook client), assume it's capable
        }

        if (metrics.getBatteryLevel() > 0 && metrics.getBatteryLevel() < MIN_BATTERY) {
            rejectReason.append("Battery too low (").append(metrics.getBatteryLevel()).append("%)");
            return false;
        }

        if (THERMAL_SEVERE.equalsIgnoreCase(metrics.getThermalState()) || 
            THERMAL_CRITICAL.equalsIgnoreCase(metrics.getThermalState())) {
            rejectReason.append("Thermal state is too high: ").append(metrics.getThermalState());
            return false;
        }

        return true;
    }
}
