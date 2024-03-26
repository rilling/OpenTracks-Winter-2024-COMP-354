package de.dennisguse.opentracks.data.models;

import java.util.List;

import de.dennisguse.opentracks.data.models.TrackPoint;

import android.content.Context;

import java.util.List;
import de.dennisguse.opentracks.services.TrackDifferentiate;

/**
 * Represents a ski lift object at a ski spot/resort.
 * 
 * This class provides functionality to determine whether a user is currently riding the ski lift
 * based on the data collected by a TrackDifferentiate object. It encapsulates information about
 * the ski lift such as its name, number, average speed, and type.
 * 
 * Usage:
 * - Create an instance of SkiLift by providing its name, number, average speed, lift type,
 *   and a TrackDifferentiate object.
 * - Use the isUserRidingSkiLift() method to check if the user is currently riding the ski lift, useful for service functions.
 * 
 * TODO :
 * - Implement some GPX reference for the SkiLift's location, as it's a physical object it should be placed in the map
 *
 */
public class SkiLift {
    private String name;
    private int number;
    private double averageSpeed;
    private String liftType;
    private TrackDifferentiate trackDifferentiate; // Use service trackDifferentiate to determine SkiLift riding status
    
    // Constructor
    public SkiLift(String name, int number, double averageSpeed, String liftType, TrackDifferentiate trackDifferentiate) {
        this.name = name;
        this.number = number;
        this.averageSpeed = averageSpeed;
        this.liftType = liftType;
        this.trackDifferentiate = trackDifferentiate;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getLiftType() {
        return liftType;
    }

    public void setLiftType(String liftType) {
        this.liftType = liftType;
    }
    
    // Method to determine if the user is riding the ski lift
    public boolean isUserRidingSkiLift(List<TrackPoint> trackPoints) {
        if (trackPoints.size() < 2) {
            return false; // Not enough data, now what the appropriate threshold for this is, not yet clear.
        }

        // Thresholds to determine movement of ski lift, need to test with data to determine appropriate values
        double altitudeChangeThreshold = 10.0;
        double speedThreshold = 2.0;

        // Get the first and last track points
        TrackPoint firstPoint = trackPoints.get(0);
        TrackPoint lastPoint = trackPoints.get(trackPoints.size() - 1);

        // Check if altitude has increased significantly
        double altitudeChange = firstPoint.getAltitude().toM() - lastPoint.getAltitude().toM();
        if (altitudeChange < altitudeChangeThreshold) {
            return false; // Altitude change is not significant
        }

        // Calculate total distance
        double totalDistance = calculateTotalDistance(trackPoints);

        // Calculate total time
        double totalTime = calculateTotalTime(trackPoints);

        // Calculate average speed
        double averageSpeed = totalDistance / totalTime;

        // Check if average speed is below a certain threshold
        if (averageSpeed > speedThreshold) {
            return false; // Average speed is too high
        }

        // If both conditions are met, user is riding the ski lift
        return true;
    }

    // Helper method to calculate total distance covered
    private double calculateTotalDistance(List<TrackPoint> trackPoints) {
        double totalDistance = 0.0;
        return totalDistance;
    }

    // Helper method to calculate total time duration
    private double calculateTotalTime(List<TrackPoint> trackPoints) {
        return 0.0; // Need to think this out more lol
    }
}
