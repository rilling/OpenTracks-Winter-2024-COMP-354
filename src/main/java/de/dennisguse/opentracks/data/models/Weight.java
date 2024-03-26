package de.dennisguse.opentracks.data.models;

import de.dennisguse.opentracks.settings.UnitSystem;

public record Weight(int weight_kg) {
    public static Weight of(int weight_kg) {
        return new Weight(weight_kg);
    }

    public static Weight zero() {
        return of(0);
    }

    public static Weight max(Weight speed1, Weight speed2) {
        if (speed1.greaterThan(speed2)) {
            return speed1;
        }

        return speed2;
    }

    public boolean isZero() {
        return weight_kg == 0;
    }

    public boolean isInvalid() {
        return Double.isInfinite(weight_kg);
    }

    public boolean lessThan(Weight weight) {
        return !greaterThan(weight);
    }

    public boolean greaterThan(Weight height) {
        return weight_kg > height.weight_kg;
    }

    public boolean greaterOrEqualThan(Weight height) {
        return weight_kg >= height.weight_kg;
    }

    public double toCM() { return weight_kg; }

    public double toLb() {
        return weight_kg * 2.2;
    }

    public double to(UnitSystem unitSystem) {
        return switch (unitSystem) {
            case METRIC -> toCM();
            default -> toLb();
        };
    }
}
