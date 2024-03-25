package de.dennisguse.opentracks.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.dennisguse.opentracks.data.models.AltitudeGainLoss;
import de.dennisguse.opentracks.data.models.TrackPoint;
public class SegmentSplits {
    public static Map<String, List<TrackPoint>> splitSegments(List<TrackPoint> trackPoints) {
        Map<String, List<TrackPoint>> segments = new HashMap<>();
        segments.put("Skiing", new ArrayList<>());
        segments.put("Chairlift", new ArrayList<>());
        segments.put("Waiting", new ArrayList<>());
        AltitudeGainLoss altitudeGainLoss = new AltitudeGainLoss(0, 0);

        for (int i = 1; i < trackPoints.size(); i++) {
            TrackPoint cPoint = trackPoints.get(i);

            if (altitudeGainLoss.shouldStartNewSegment(trackPoints, i)) {
                if (!segments.get("Skiing").isEmpty() && altitudeGainLoss.isSkiing()) {
                    segments.get("Skiing").add(cPoint);
                } else if (!segments.get("Chairlift").isEmpty() && altitudeGainLoss.isChairlift()) {
                    segments.get("Chairlift").add(cPoint);
                } else {
                    segments.get("Waiting").add(cPoint);
                }
            } else {
                if (altitudeGainLoss.isSkiing()) {
                    segments.get("Skiing").add(cPoint);
                } else if (altitudeGainLoss.isChairlift()) {
                    segments.get("Chairlift").add(cPoint);
                } else {
                    segments.get("Waiting").add(cPoint);
                }
            }
        }

        return segments;
    }
}
