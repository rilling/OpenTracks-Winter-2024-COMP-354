package de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats;

import androidx.annotation.NonNull;

/**
 * An enum of all metrics available in the visualize daily statistics feature.
 */
public enum Metric {
    AVG_SPEED("Avg Speed"),
    AVG_SLOPE("Avg Slope"),
    CHAIRLIFT_SPEED("Chairlift Speed"),
    TOTAL_DISTANCE("Total Distance");

    private final String displayName;

    Metric(String displayName) {
        this.displayName = displayName;
    }

    @NonNull
    @Override
    public String toString() {
        return displayName;
    }
}
