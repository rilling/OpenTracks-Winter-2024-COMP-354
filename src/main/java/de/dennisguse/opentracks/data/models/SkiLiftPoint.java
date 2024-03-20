package de.dennisguse.opentracks.data.models;

import androidx.annotation.NonNull;

/**
 * A specialized TrackPoint representing a point during a ski lift ride.
 * Inherits from TrackPoint class.
 */
public class SkiLiftPoint extends TrackPoint {

    // Constructor
    public SkiLiftPoint(@NonNull Type type, @NonNull Instant time) {
        super(type, time);
    }
}
