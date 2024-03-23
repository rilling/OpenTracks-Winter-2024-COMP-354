package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.widget.TextView;

import de.dennisguse.opentracks.R;

public class DummyDataGenerator {
    public double getSharpeningPercentageValue() {
        return 0.25;
    }
    public double getKmSinceLastSharpeningValue() {
        return 11.0;
    }
    public double getWaxingPercentageValue() {
        return 0.33;
    }
    public double getKmSinceLastWaxingValue() { return 44.0; }
    public String getUnit() { return "km"; }
}
