package de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats;

import androidx.annotation.NonNull;

/**
 * An enum with all data points frequency available in the visualize daily statistics feature.
 */
public enum Frequency {
    THREE(3),
    FIVE(5);

    private final int value;

    Frequency(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
