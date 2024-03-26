/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package de.dennisguse.opentracks.stats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import de.dennisguse.opentracks.data.models.ActivityType;
import de.dennisguse.opentracks.data.models.Altitude;
import de.dennisguse.opentracks.data.models.AltitudeGainLoss;
import de.dennisguse.opentracks.data.models.Distance;
import de.dennisguse.opentracks.data.models.HeartRate;
import de.dennisguse.opentracks.data.models.Speed;
import de.dennisguse.opentracks.data.models.Track;
import de.dennisguse.opentracks.data.models.TrackPoint;

/**
 * Statistical data about a {@link Track}.
 * The data in this class should be filled out by {@link TrackStatisticsUpdater}.
 *
 * @author Rodrigo Damazio
 */
//TODO Use null instead of Double.isInfinite
//TODO Check that data ranges are valid (not less than zero etc.)
//TODO Should be a Java record
public class TrackStatistics {

    // The min and max altitude (meters) seen on this track.
    private final ExtremityMonitor altitudeExtremities = new ExtremityMonitor();

    // The total number of runs in a season.
    private int totalRunsSeason;

    // The total number of days skied in a season.
    private int totalSkiDaysSeason;

    // The total distance skied (consider all tracks) in a season.
    private Distance totalTrackDistanceSeason;

    // The total distance covered while in vertical descents in a season.
    private Distance totalVerticalDescentSeason;

    // The average speed in a season.
    private Speed avgSpeedSeason;

    // The slope percentage in a season.
    private double slopePercentageSeason;
    // The track start time.
    private Instant startTime;
    // The track stop time.
    private Instant stopTime;

    private Distance totalDistance;
    // Updated when new points are received, may be stale.
    private Duration totalTime;
    // Based on when we believe the user is traveling.
    private Duration movingTime;
    // The maximum speed (meters/second) that we believe is valid.
    private Speed maxSpeed;
    private Float totalAltitudeGain_m = null;
    private Float totalAltitudeLoss_m = null;
    // The average heart rate seen on this track
    private HeartRate avgHeartRate = null;

    private boolean isIdle;

    //==========================================================================//
    private Duration chairliftWaitingTime;
    private Distance chairliftElevationGain;
    private Speed chairliftConstantSpeed;

    private Duration skiingWaitingTime;
    private Distance skiingDistance;
    private Speed skiingSpeed;

    public Duration getSkiingWaitingTime() {
        return skiingWaitingTime;
    }

    public void setSkiingWaitingTime(Duration skiingWaitingTime) {
        this.skiingWaitingTime = skiingWaitingTime;
    }

    public Distance getSkiingDistance() {
        return skiingDistance;
    }

    public void setSkiingDistance(Distance skiingDistance) {
        this.skiingDistance = skiingDistance;
    }

    public Speed getSkiingSpeed() {
        return skiingSpeed;
    }

    public void setSkiingSpeed(Speed skiingSpeed) {
        this.skiingSpeed = skiingSpeed;
    }

    public Duration getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Duration waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Distance getWaitingTimeDistance() {
        return waitingTimeDistance;
    }

    public void setWaitingTimeDistance(Distance waitingTimeDistance) {
        this.waitingTimeDistance = waitingTimeDistance;
    }

    public Speed getWaitingTimeSpeed() {
        return waitingTimeSpeed;
    }

    public void setWaitingTimeSpeed(Speed waitingTimeSpeed) {
        this.waitingTimeSpeed = waitingTimeSpeed;
    }

    private Duration waitingTime;
    private Distance waitingTimeDistance;
    private Speed waitingTimeSpeed;


    private boolean waiting;
    private boolean skiing;
    private boolean chairlift;

    //===========================================================================//


    public TrackStatistics() {
        reset();
    }

    /**
     * Copy constructor.
     *
     * @param other another statistics data object to copy from
     */
    public TrackStatistics(TrackStatistics other) {
        startTime = other.startTime;
        stopTime = other.stopTime;
        totalDistance = other.totalDistance;
        totalTime = other.totalTime;
        movingTime = other.movingTime;
        maxSpeed = other.maxSpeed;
        altitudeExtremities.set(other.altitudeExtremities.getMin(), other.altitudeExtremities.getMax());
        totalAltitudeGain_m = other.totalAltitudeGain_m;
        totalAltitudeLoss_m = other.totalAltitudeLoss_m;
        avgHeartRate = other.avgHeartRate;
        isIdle = other.isIdle;
        //===========================================//
        chairliftConstantSpeed = other.chairliftConstantSpeed;
        chairliftElevationGain = other.chairliftElevationGain;
        chairliftWaitingTime = other.chairliftWaitingTime;
        //===========================================//
    }

    @VisibleForTesting
    public TrackStatistics(String startTime, String stopTime, double totalDistance_m, int totalTime_s, int movingTime_s, float maxSpeed_mps, Float totalAltitudeGain_m, Float totalAltitudeLoss_m) {
        this.startTime = Instant.parse(startTime);
        this.stopTime = Instant.parse(stopTime);
        this.totalDistance = Distance.of(totalDistance_m);
        this.totalTime = Duration.ofSeconds(totalTime_s);
        this.movingTime = Duration.ofSeconds(movingTime_s);
        this.maxSpeed = Speed.of(maxSpeed_mps);
        this.totalAltitudeGain_m = totalAltitudeGain_m;
        this.totalAltitudeLoss_m = totalAltitudeLoss_m;
    }

    /**
     * Combines these statistics with those from another object.
     * This assumes that the time periods covered by each do not intersect.
     *
     * @param other another statistics data object
     */
    public void merge(TrackStatistics other) {
        if (startTime == null) {
            startTime = other.startTime;
        } else {
            startTime = startTime.isBefore(other.startTime) ? startTime : other.startTime;
        }
        if (stopTime == null) {
            stopTime = other.stopTime;
        } else {
            stopTime = stopTime.isAfter(other.stopTime) ? stopTime : other.stopTime;
        }

        if (avgHeartRate == null) {
            avgHeartRate = other.avgHeartRate;
        } else {
            if (other.avgHeartRate != null) {
                // Using total time as weights for the averaging.
                // Important to do this before total time is updated
                avgHeartRate = HeartRate.of(
                        (totalTime.getSeconds() * avgHeartRate.getBPM() + other.totalTime.getSeconds() * other.avgHeartRate.getBPM())
                                / (totalTime.getSeconds() + other.totalTime.getSeconds())
                );
            }
        }

        totalDistance = totalDistance.plus(other.totalDistance);
        totalTime = totalTime.plus(other.totalTime);
        movingTime = movingTime.plus(other.movingTime);
        maxSpeed = Speed.max(maxSpeed, other.maxSpeed);
        if (other.altitudeExtremities.hasData()) {
            altitudeExtremities.update(other.altitudeExtremities.getMin());
            altitudeExtremities.update(other.altitudeExtremities.getMax());
        }
        if (totalAltitudeGain_m == null) {
            if (other.totalAltitudeGain_m != null) {
                totalAltitudeGain_m = other.totalAltitudeGain_m;
            }
        } else {
            if (other.totalAltitudeGain_m != null) {
                totalAltitudeGain_m += other.totalAltitudeGain_m;
            }
        }
        if (totalAltitudeLoss_m == null) {
            if (other.totalAltitudeLoss_m != null) {
                totalAltitudeLoss_m = other.totalAltitudeLoss_m;
            }
        } else {
            if (other.totalAltitudeLoss_m != null) {
                totalAltitudeLoss_m += other.totalAltitudeLoss_m;
            }
        }
    }

    public boolean isInitialized() {
        return startTime != null;
    }

    public void reset() {
        startTime = null;
        stopTime = null;

        setTotalDistance(Distance.of(0));
        setTotalTime(Duration.ofSeconds(0));
        setMovingTime(Duration.ofSeconds(0));
        setMaxSpeed(Speed.zero());
        setTotalAltitudeGain(null);
        setTotalAltitudeLoss(null);

        isIdle = false;


    }

    public void reset(Instant startTime) {
        reset();
        setStartTime(startTime);
    }

    public Instant getStartTime() {
        return startTime;
    }

    /**
     * Should only be called on start.
     */
    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
        setStopTime(startTime);
    }

    public Instant getStopTime() {
        return stopTime;
    }

    public void setStopTime(Instant stopTime) {
        if (stopTime.isBefore(startTime)) {
            // Time must be monotonically increasing, but we might have events at the same point in time (BLE and GPS)
            throw new RuntimeException("stopTime cannot be less than startTime: " + startTime + " " + stopTime);
        }
        this.stopTime = stopTime;
    }

    public Distance getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Distance totalDistance_m) {
        this.totalDistance = totalDistance_m;
    }

    public void addTotalDistance(Distance distance_m) {
        totalDistance = totalDistance.plus(distance_m);
    }

    /**
     * Gets the total time in milliseconds that this track has been active.
     * This statistic is only updated when a new point is added to the statistics, so it may be off.
     * If you need to calculate the proper total time, use {@link #getStartTime} with the current time.
     */
    public Duration getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Duration totalTime) {
        this.totalTime = totalTime;
    }

    public Duration getMovingTime() {
        return movingTime;
    }

    public void setMovingTime(Duration movingTime) {
        this.movingTime = movingTime;
    }

    public void addMovingTime(TrackPoint trackPoint, TrackPoint lastTrackPoint) {
        addMovingTime(Duration.between(lastTrackPoint.getTime(), trackPoint.getTime()));
    }

    //================================================================================//
    public Duration getChairliftWaitingTime(){
        return chairliftWaitingTime;
    }
    public void setChairliftWaitingTime(Duration chairliftWaitingTime){
        this.chairliftWaitingTime = chairliftWaitingTime;
    }
    public Distance getChairliftElevationGain(){
        return chairliftElevationGain;
    }
    public void setChairliftElevationGain(Distance chairliftElevationGain){
        this.chairliftElevationGain = chairliftElevationGain;
    }
    public Speed getChairliftConstantSpeed(){
        return chairliftConstantSpeed;
    }
    public void setChairliftConstantSpeed(Speed chairliftConstantSpeed){
        this.chairliftConstantSpeed = chairliftConstantSpeed;
    }
    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    public void addMovingTime(Duration time) {
        if (time.isNegative()) {
            throw new RuntimeException("Moving time cannot be negative");
        }
        movingTime = movingTime.plus(time);
    }

    public Duration getStoppedTime() {
        return totalTime.minus(movingTime);
    }

    public boolean isIdle() {
        return isIdle;
    }

    public void setIdle(boolean idle) {
        isIdle = idle;
    }

    public boolean hasAverageHeartRate() {
        return avgHeartRate != null;
    }

    @Nullable
    public HeartRate getAverageHeartRate() {
        return avgHeartRate;
    }

    /**
     * Gets the average speed.
     * This calculation only takes into account the displacement until the last point that was accounted for in statistics.
     */
    public Speed getAverageSpeed() {
        if (totalTime.isZero()) {
            return Speed.of(0);
        }
        return Speed.of(totalDistance.toM() / totalTime.getSeconds());
    }

    public Speed getAverageMovingSpeed() {
        return Speed.of(totalDistance, movingTime);
    }

    public Speed getMaxSpeed() {
        return Speed.max(maxSpeed, getAverageMovingSpeed());
    }

    public void setMaxSpeed(Speed maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public boolean hasAltitudeMin() {
        return !Double.isInfinite(getMinAltitude());
    }

    public double getMinAltitude() {
        return altitudeExtremities.getMin();
    }

    public void setMinAltitude(double altitude_m) {
        altitudeExtremities.setMin(altitude_m);
    }

    public boolean hasAltitudeMax() {
        return !Double.isInfinite(getMaxAltitude());
    }

    /**
     * Gets the maximum altitude.
     * This is calculated from the smoothed altitude, so this can actually be less than the current altitude.
     */
    public double getMaxAltitude() {
        return altitudeExtremities.getMax();
    }

    public void setMaxAltitude(double altitude_m) {
        altitudeExtremities.setMax(altitude_m);
    }

    public void updateAltitudeExtremities(Altitude altitude) {
        if (altitude != null) {
            altitudeExtremities.update(altitude.toM());
        }
    }

    public void setAverageHeartRate(HeartRate heartRate) {
        if (heartRate != null) {
            avgHeartRate = heartRate;
        }
    }

    public boolean hasTotalAltitudeGain() {
        return totalAltitudeGain_m != null;
    }

    @Nullable
    public Float getTotalAltitudeGain() {
        return totalAltitudeGain_m;
    }

    public void setTotalAltitudeGain(Float totalAltitudeGain_m) {
        this.totalAltitudeGain_m = totalAltitudeGain_m;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    public void addTotalAltitudeGain(float gain_m) {
        if (totalAltitudeGain_m == null) {
            totalAltitudeGain_m = 0f;
        }
        totalAltitudeGain_m += gain_m;
    }

    public boolean hasTotalAltitudeLoss() {
        return totalAltitudeLoss_m != null;
    }

    @Nullable
    public Float getTotalAltitudeLoss() {
        return totalAltitudeLoss_m;
    }

    public void setTotalAltitudeLoss(Float totalAltitudeLoss_m) {
        this.totalAltitudeLoss_m = totalAltitudeLoss_m;
    }
    public int getTotalRunsSeason(){
        return this.totalRunsSeason;
    }

    public void setTotalRunsSeason(int totalRunsSeason){
        this.totalRunsSeason = totalRunsSeason;
    }


    public int getTotalSkiDaysSeason(){
        return this.totalSkiDaysSeason;
    }

    public void setTotalSkiDaysSeason(int totalSkiDaysSeason){
        this.totalSkiDaysSeason = totalSkiDaysSeason;
    }


    public Distance getTotalTrackDistanceSeason(){
        return this.totalTrackDistanceSeason;
    }

    public void setTotalTrackDistanceSeason(Distance totalTrackDistanceSeason){
        this.totalTrackDistanceSeason = totalTrackDistanceSeason;
    }


    public Distance getTotalVerticalDescentSeasonSeason(){
        return this.totalVerticalDescentSeason;
    }

    public void setTotalVerticalDescentSeason(Distance totalVerticalDescentSeason){
        this.totalVerticalDescentSeason = totalVerticalDescentSeason;
    }


    public Speed getAvgSpeedSeason(){
        return this.avgSpeedSeason;
    }

    public void setAvgSpeedSeason(Speed avgSpeedSeason){
        this.avgSpeedSeason = avgSpeedSeason;
    }


    public double getSlopePercentageSeason(){
        return this.slopePercentageSeason;
    }

    public void setSlopePercentageSeason(double slopePercentageSeason){
        this.slopePercentageSeason = slopePercentageSeason;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    public void addTotalAltitudeLoss(float loss_m) {
        if (totalAltitudeLoss_m == null) {
            totalAltitudeLoss_m = 0f;
        }
        totalAltitudeLoss_m += loss_m;
    }

    public static TrackStatistics sumOfTotalStats(List<TrackStatistics> trackStatistics)
    {
        TrackStatistics sum = new TrackStatistics();

        int totalRuns = 0;
        int totalSkiDays = 0;
        Distance totalTrackDistance = Distance.of(0);
        Distance totalVerticalDescent = Distance.of(0);
        double totalAvgSpeed = 0;
        double totalSlopePercentage = 0;

        for(TrackStatistics trackStat : trackStatistics)
        {
            totalRuns += trackStat.getTotalRunsSeason();
            totalSkiDays += trackStat.getTotalSkiDaysSeason();
            totalTrackDistance = totalTrackDistance.plus(trackStat.getTotalTrackDistanceSeason());
            totalVerticalDescent = totalVerticalDescent.plus(trackStat.getTotalVerticalDescentSeasonSeason());
            totalAvgSpeed += trackStat.getAvgSpeedSeason().speed_mps();
            totalSlopePercentage += trackStat.getSlopePercentageSeason();
        }

        int numTracks = trackStatistics.size();
        totalAvgSpeed /= numTracks;
        totalSlopePercentage /= numTracks;

        sum.setTotalRunsSeason(totalRuns);
        sum.setTotalSkiDaysSeason(totalSkiDays);
        sum.setTotalTrackDistanceSeason(totalTrackDistance);
        sum.setTotalVerticalDescentSeason(totalVerticalDescent);
        sum.setAvgSpeedSeason(Speed.of(totalAvgSpeed));
        sum.setSlopePercentageSeason(totalSlopePercentage);

        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackStatistics)) return false;

        return toString().equals(o.toString());
    }

    @NonNull
    @Override
    public String toString() {
        return "TrackStatistics { Start Time: " + getStartTime() + "; Stop Time: " + getStopTime()
                + "; Total Distance: " + getTotalDistance() + "; Total Time: " + getTotalTime()
                + "; Moving Time: " + getMovingTime() + "; Max Speed: " + getMaxSpeed()
                + "; Min Altitude: " + getMinAltitude() + "; Max Altitude: " + getMaxAltitude()
                + "; Altitude Gain: " + getTotalAltitudeGain()
                + "; Altitude Loss: " + getTotalAltitudeLoss()
                + "; Chairlift Waiting Time: " + getChairliftWaitingTime()
                + "; Chairlift Elevation Gain: " + getChairliftElevationGain()
                + "; Chairlift Constant Speed: " + getChairliftConstantSpeed()+ "}";
    }
}