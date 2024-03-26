package de.dennisguse.opentracks.data.models;

import android.content.Context;
import android.content.res.Resources;
import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.Objects;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.settings.UnitSystem;
import de.dennisguse.opentracks.util.StringUtils;

public class HeightFormatter {

    private final Resources resources;

    private final int decimalCount;

    private final UnitSystem unitSystem;

    private HeightFormatter(Resources resources, int decimalCount, UnitSystem unitSystem) {
        this.resources = resources;
        this.decimalCount = decimalCount;
        this.unitSystem = unitSystem;
    }

    /**
     * Gets the formatted height with unit.
     *
     * @return the formatted height (or null) and it's unit as {@link Pair}
     */
    public Pair<String, String> getHeightParts(Height height) {
        if (height == null) {
            height = Height.zero();
        }

        if (Objects.requireNonNull(unitSystem) == UnitSystem.METRIC) {
            String unitString = resources.getString(R.string.unit_centimeter);

            return new Pair<>(StringUtils.formatDecimal(height.to(unitSystem), 0), unitString);
        }
        else {
            String feetUnit = resources.getString(R.string.unit_feet_symbol);
            String inchesUnit = resources.getString(R.string.unit_inches_symbol);

            double totalInches = height.to(unitSystem);
            int feet = (int) (totalInches / 12);
            int inches = (int) totalInches % 12;

            return new Pair<>(feet + feetUnit, inches + inchesUnit);
        }
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private int decimalCount;

        private UnitSystem unitSystem;

        public Builder() {
            decimalCount = 2;
        }

        public Builder setDecimalCount(int decimalCount) {
            this.decimalCount = decimalCount;
            return this;
        }

        public Builder setUnit(@NonNull UnitSystem unitSystem) {
            this.unitSystem = unitSystem;
            return this;
        }

        public HeightFormatter build(Resources resource) {
            return new HeightFormatter(resource, decimalCount, unitSystem);
        }

        public HeightFormatter build(Context context) {
            return build(context.getResources());
        }
    }
}
