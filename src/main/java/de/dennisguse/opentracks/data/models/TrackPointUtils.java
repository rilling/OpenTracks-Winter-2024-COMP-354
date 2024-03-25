/*
 * This class implements a utility for calculating the moving average of speeds from track points.
 * Portions of this code were generated with the assistance of ChatGPT-4,
 * an AI developed by OpenAI (https://openai.com/).
 * The code has been modified for the specific use case.
 */
package de.dennisguse.opentracks.data.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.time.Duration;
import java.time.Instant;

import de.dennisguse.opentracks.data.TrackPointIterator;
import de.dennisguse.opentracks.data.models.TrackPoint;

public class TrackPointUtils {

    // Set a constant for the sample interval as 5 seconds.
    // This means we will calculate using data points that are 5 seconds apart.
    private static final Duration SAMPLE_INTERVAL = Duration.ofSeconds(5);
    /**
     * Calculates the moving average of speeds from track points.
     *
     * @param iterator      An iterator over TrackPoints.
     * @param numDataPoints The number of data points to include in the moving average.
     * @return A list of calculated moving averages.
     */
    public static List<Double> calculateSpeedMovingAverageWithIterator(TrackPointIterator iterator, int numDataPoints) {
        // Create a list to store the calculated moving averages.
        // This is where the final results will be stored.
        List<Double> movingAverages = new ArrayList<>();

        // LinkedList to store the most recent track points we're considering for the moving average.
        LinkedList<TrackPoint> window = new LinkedList<>();

        // Initialize a variable to sum the speeds of the points in the window.
        double sumOfSpeeds = 0;

        // Variable to remember the start time of the window of points we're looking at.
        Instant windowStartTime = null;

        // Use a while loop to go through each track point provided by the iterator.
        while (iterator.hasNext()) {
            // Get the next track point from the iterator.
            TrackPoint currentPoint = iterator.next();
            // Get the time at which this track point was recorded.
            Instant currentTime = currentPoint.getTime();

            // If this is our first track point or it's been 5 seconds since the last one we looked at,
            // we add this track point in our calculations.
            if (windowStartTime == null || Duration.between(windowStartTime, currentTime).compareTo(SAMPLE_INTERVAL) >= 0) {
                // If the track point has a valid speed value, then continue with the calculation.
                if (currentPoint.hasSpeed() && !currentPoint.getSpeed().isInvalid()) {
                    // Convert the speed from the Speed object to km/h.
                    double currentSpeedKMH = currentPoint.getSpeed().toKMH();
                    // Add this km/h speed to the sum of speeds.
                    sumOfSpeeds += currentSpeedKMH;
                    // Add this track point to the end of our LinkedList (window).
                    window.addLast(currentPoint);

                    // If the window now has the right number of points we want for our calculation,
                    if (window.size() == numDataPoints) {
                        // Calculate the moving average by dividing the sum of speeds by the number of points.
                        double movingAverageKMH = sumOfSpeeds / numDataPoints;
                        // Add the calculated moving average to the result list.
                        movingAverages.add(movingAverageKMH);
                    }
                }

                // If the window has grown beyond the number of points we want (e.g. due to data being added above),
                // then remove the oldest point to maintain the window size.
                if (window.size() > numDataPoints) {
                    // Subtract the speed of the oldest point from the sum of speeds.
                    sumOfSpeeds -= window.removeFirst().getSpeed().toKMH();
                }

                // Update the start time for the next window of points.
                windowStartTime = currentTime;
                // Reset the sum of speeds and the window for the next set of points.
                sumOfSpeeds = currentPoint.getSpeed().toKMH();
                // Reset list for next movable avg calculation
                window.clear();
                // Add the current track point to the end of the window list for processing
                window.addLast(currentPoint);
            }
        }

        // Return the list of moving averages
        return movingAverages;
    }
}