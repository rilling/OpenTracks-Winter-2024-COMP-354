package de.dennisguse.opentracks.data.models;

public record AltitudeGainLoss(float gain_m, float loss_m) {
    //to differentiate between chairlift and skiing.
    private boolean isSkiing;
    private boolean isUsingChairLift;

    private boolean isWaiting;
}
