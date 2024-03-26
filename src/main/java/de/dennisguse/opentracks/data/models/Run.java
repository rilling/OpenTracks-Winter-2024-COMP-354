package de.dennisguse.opentracks.data.models;

import java.util.List;

public class Run {

    private double totalRunElevation;
    private List<TrackPoint> trackPointsList;

    /**
     * This is a helper method which calculates the elevation gain for a specific run based on its
     * track points
     */
    public void calculateRunSpecificElevationGain()
    {
        double prevElevation = this.trackPointsList.get(0).getAltitude().toM();
        for (TrackPoint tp:
             this.trackPointsList) {
            Altitude altitude = tp.getAltitude();
            double tpElevationGain = prevElevation - altitude.toM();
            this.totalRunElevation += tpElevationGain;
            prevElevation = altitude.toM();
        }
    }

    public double getTotalRunElevation() {
        return totalRunElevation;
    }

    public void setTotalRunElevation(double totalRunElevation) {
        this.totalRunElevation = totalRunElevation;
    }
}
