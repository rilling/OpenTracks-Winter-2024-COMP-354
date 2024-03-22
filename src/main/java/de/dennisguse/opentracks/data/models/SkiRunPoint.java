package de.dennisguse.opentracks.data.models;

import androidx.annotation.NonNull;

import java.time.Instant;

/**
 * A specialized TrackPoint representing a point during a ski run.
 * Inherits from TrackPoint class.
 */
public class SkiRunPoint extends TrackPoint {

    // Constructor
    public SkiRunPoint(@NonNull Type type, @NonNull Instant time) {
        super(type, time);
    }
}
