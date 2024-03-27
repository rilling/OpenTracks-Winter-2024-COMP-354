package de.dennisguse.opentracks.data.models;

import java.time.Duration;
import java.util.List;

public class SkiRun {
	    private String name;
	    private List<TrackPoint> trackPoints;

	    // Constructor
	    public SkiRun(String name, List<TrackPoint> trackPoints) {
	        this.name = name;
	        this.trackPoints = trackPoints;
	    }

	    // Getters and Setters
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public List<TrackPoint> getTrackPoints() {
	        return trackPoints;
	    }

	    public void setTrackPoints(List<TrackPoint> trackPoints) {
	        this.trackPoints = trackPoints;
	    }

	    // Determine the start and end points of the ski run
	    public TrackPoint getStartPoint() {
	        if (trackPoints.isEmpty()) {
	            return null;
	        }
	        return trackPoints.get(0);
	    }

	    public TrackPoint getEndPoint() {
	        if (trackPoints.isEmpty()) {
	            return null;
	        }
	        return trackPoints.get(trackPoints.size() - 1);
	    }

	    // Calculate the duration of the ski run
	    public Duration getDuration() {
	        if (trackPoints.isEmpty()) {
	            return Duration.ZERO;
	        }
	        TrackPoint startPoint = getStartPoint();
	        TrackPoint endPoint = getEndPoint();
	        return Duration.between(startPoint.getTime(), endPoint.getTime());
	    }

	    // Method to calculate the total distance covered during the ski run
	    public double getTotalDistance() {
	        double totalDistance = 0.0;
	        for (int i = 1; i < trackPoints.size(); i++) {
	            TrackPoint pPoint = trackPoints.get(i - 1);
	            TrackPoint cPoint = trackPoints.get(i);
	            Distance distance = pPoint.distanceToPrevious(cPoint);
	            totalDistance += distance.toKM();
	        }
	        return totalDistance;
	    }

	    // Determine if the user was skiing
	    public boolean isUserSkiing() {
	    	if (trackPoints.size() < 2) {
	            return false; // Not enough data
	        }

	        // Thresholds to determine skiing activity 
	        double altitudeChangeThreshold = 10.0; // Meters
	        double speedThreshold = 5.0; // Meters per second
	        long timeThresholdInSeconds = 60; // Seconds

	        // Get the first and last track points
	        TrackPoint startPoint = getStartPoint();
	        TrackPoint endPoint = getEndPoint();

	        // Check if altitude change is significant
	        double altitudeChange = Math.abs(startPoint.getAltitude().toM() - endPoint.getAltitude().toM());
	        if (altitudeChange < altitudeChangeThreshold) {
	            return false; // Altitude change not significant, likely not skiing
	        }

	        // Calculate total distance
	        double totalDistance = getTotalDistance();

	        // Calculate total time (in seconds)
	        long totalTimeInSeconds = getDuration().getSeconds();

	        // Calculate average speed
	        double averageSpeed = totalDistance / totalTimeInSeconds;

	        // Check if average speed is above the speed threshhold 
	        if (averageSpeed < speedThreshold) {
	            return false; // Average speed too slow, prob not skiing
	        }

	        // Check if total time is above time threshold
	        if (totalTimeInSeconds < timeThresholdInSeconds) {
	            return false; // Duration too short, prob not skiing
	        }

	        return true; // User is likely skiing
	    }
	}