package de.dennisguse.opentracks.data.models;

import androidx.annotation.NonNull;

import de.dennisguse.opentracks.R;

public abstract class Altitude {

    private final double altitude_m;

    private Altitude(double altitude_m) {
        this.altitude_m = altitude_m;
    }

    public double toM() {
        return altitude_m;
    }
    
    public abstract int getLabelId();

    public static class WGS84 extends Altitude {

        private WGS84(double altitude_m) {
            super(altitude_m);
        }

        @Override
        public int getLabelId() {
            return R.string.wgs84;
        }

        public static WGS84 of(double altitude_m) {
            return new WGS84(altitude_m);
        }
    }

    public static class EGM2008 extends Altitude {

        private EGM2008(double altitude_m) {
            super(altitude_m);
        }

        @Override
        public int getLabelId() {
            return R.string.egm2008;
        }

        public static EGM2008 of(double altitude_m) {
            return new EGM2008(altitude_m);
        }
    }

    // returns an int based on this altitude and the right-hand side.
    // returns 1 if this altitude is greater
    // returns 0 if both altitudes are equal
    // returns -1 if this altitude is smaller
    public int compare(Altitude rhs) {
        if (this.altitude_m > rhs.altitude_m) {
            return 1;
        } else if (this.altitude_m > rhs.altitude_m) {
            return 0;
        }
        return -1;
    }

    @NonNull
    @Override
    public String toString() {
        return "Altitude{" +
                "altitude_m=" + altitude_m + this.getClass().getSimpleName() +
                '}';
    }
}

