package de.dennisguse.opentracks.data.models;

public record AltitudeGainLoss(float gain_m, float loss_m) {
    //to differentiate between chairlift and skiing.
    private static boolean isSkiing;
    private static boolean isUsingChairLift;
    private static boolean isWaiting;

    public AltitudeGainLoss(float gain_m, float loss_m){
        this.gain_m = gain_m;
        this.loss_m = loss_m;
        this.isSkiing = loss_m > 0;
        this.isUsingChairLift = gain_m < 0;
        this.isWaiting = isWaiting;
    }

    public boolean isSkiing() {
        return isSkiing;
    }

    public boolean isUsingChairLift() {
        return isUsingChairLift;
    }

    public boolean isWaiting() {
        return isWaiting;
    }





}
