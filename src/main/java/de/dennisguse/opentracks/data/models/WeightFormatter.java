package de.dennisguse.opentracks.data.models;

import android.content.Context;
import android.content.res.Resources;
import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.Objects;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.settings.UnitSystem;
import de.dennisguse.opentracks.util.StringUtils;

public class WeightFormatter {

    private final Resources resources;

    private final int decimalCount;

    private final UnitSystem unitSystem;

    private WeightFormatter(Resources resources, int decimalCount, UnitSystem unitSystem) {
        this.resources = resources;
        this.decimalCount = decimalCount;
        this.unitSystem = unitSystem;
    }

    /**
     * Gets the formatted height with unit.
     *
     * @return the formatted height (or null) and it's unit as {@link Pair}
     */
    public Pair<String, String> getWeightParts(Weight weight) {
        int unitId = switch (unitSystem) {
            case METRIC ->
                R.string.unit_kilograms;
            default ->
                R.string.unit_pounds;
        };

        String unitString = resources.getString(unitId);

        if (weight == null) {
            weight = Weight.zero();
        }

        return new Pair<>(StringUtils.formatDecimal(weight.to(unitSystem), 0), unitString);
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

        public WeightFormatter build(Resources resource) {
            return new WeightFormatter(resource, decimalCount, unitSystem);
        }

        public WeightFormatter build(Context context) {
            return build(context.getResources());
        }
    }
}
