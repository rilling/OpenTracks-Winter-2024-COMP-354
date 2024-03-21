package de.dennisguse.opentracks.data.models;

import java.util.List;

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
    
    // Method to determine if the user is riding the ski lift based on status of most recent TrackPoint
    public boolean isUserRidingSkiLift() {
        // Differentiate current point to determine if in riding status
        trackDifferentiate.differentiate();

        // Check if the most recent track point in liftPoints is recent
        List<TrackPoint> liftPoints = trackDifferentiate.getLiftPoints();
        if (!liftPoints.isEmpty()) {
            TrackPoint mostRecentLiftPoint = liftPoints.get(liftPoints.size() - 1);
            return mostRecentLiftPoint.isRecent(); // Return if point is recent as to not get status of older time stamp point
        }

        return false;
    }
}
