package de.dennisguse.opentracks.data.models;

import java.util.List;

public record AltitudeGainLoss(float gain_m, float loss_m) {
    private static final double altitudeChangeThreshold = 10.0;

    private static boolean isSkiing;
    private static boolean isChairlift;

    public AltitudeGainLoss(float gain_m, float loss_m) {
        this.gain_m = gain_m;
        this.loss_m = loss_m;
    }

    public static boolean isSkiing(){
        return isSkiing;
    }
    public static boolean isChairlift(){
        return isChairlift;
    }
    public boolean shouldStartNewSegment(List<TrackPoint> trackPoints, int currentIndex){
        TrackPoint currentPoint = trackPoints.get(currentIndex);
        TrackPoint previousPoint = trackPoints.get(currentIndex - 1);

        Altitude currentAltitude = currentPoint.getAltitude();
        Altitude previousAltitude = previousPoint.getAltitude();

        if (currentAltitude != null && previousAltitude != null){
            double altitudeChange = currentAltitude.toM() - previousAltitude.toM();
            if (altitudeChange > altitudeChangeThreshold){
                isChairlift = true;
                isSkiing = false;
                return true;
            }
            else if (altitudeChange < altitudeChangeThreshold){
                isChairlift = false;
                isSkiing = true;
                return true;

            }
        }
        isChairlift = false;
        isSkiing = false;
        return false;
    }







}
