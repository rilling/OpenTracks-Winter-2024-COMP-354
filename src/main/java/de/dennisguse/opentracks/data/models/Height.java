package de.dennisguse.opentracks.data.models;

import java.time.Duration;

import de.dennisguse.opentracks.settings.UnitSystem;

public record Height(int height_cm) {
    public static Height of(int height_cm) {
        return new Height(height_cm);
    }

    public static Height zero() {
        return of(0);
    }

    public static Height max(Height speed1, Height speed2) {
        if (speed1.greaterThan(speed2)) {
            return speed1;
        }

        return speed2;
    }

    public boolean isZero() {
        return height_cm == 0;
    }

    public boolean isInvalid() {
        return Double.isInfinite(height_cm);
    }

    public boolean lessThan(Height height) {
        return !greaterThan(height);
    }

    public boolean greaterThan(Height height) {
        return height_cm > height.height_cm;
    }

    public boolean greaterOrEqualThan(Height height) {
        return height_cm >= height.height_cm;
    }

    public double toCM() { return height_cm; }

    public double toM() { return (double) height_cm / 100; }

    public double toIn() {
        return height_cm / 2.54;
    }

    public double to(UnitSystem unitSystem) {
        return switch (unitSystem) {
            case METRIC -> toCM();
            default -> toIn();
        };
    }
}
