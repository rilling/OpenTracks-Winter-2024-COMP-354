package de.dennisguse.opentracks.ui.runlift;

import androidx.annotation.Nullable;

import de.dennisguse.opentracks.data.TrackPointIterator;
import de.dennisguse.opentracks.data.models.TrackPoint;
import de.dennisguse.opentracks.stats.TrackStatistics;
import de.dennisguse.opentracks.stats.TrackStatisticsUpdater;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RunLiftStatistics {
    private TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();
    private final List<SkiSubActivity> skiSubActivityList;

    private double waitThreshold = 1.5;
    private int thresholdCount = 3;

    private Queue<TrackPoint> trackPointQueue = new LinkedList<>();

    private RunLiftStatistics.SkiSubActivity skiSubActivity, lastSkiSubActivity;

    public RunLiftStatistics() {
        skiSubActivity = new SkiSubActivity();
        lastSkiSubActivity = new SkiSubActivity();

        skiSubActivityList = new ArrayList<>();
        skiSubActivityList.add(lastSkiSubActivity);
    }

    public TrackPoint.Id addTrackPoints(TrackPointIterator trackPointIterator) {
        boolean newSkiSubActivityAdded = false;
        TrackPoint trackPoint = null;

        while (trackPointIterator.hasNext()) {
            trackPoint = trackPointIterator.next();

            if (!lastSkiSubActivity.getTrackPoints().isEmpty() && lastSkiSubActivity.lastTrackPoint().getAltitudeLoss() != trackPoint.getAltitudeLoss()) {
                skiSubActivity = new SkiSubActivity();

                trackStatisticsUpdater = new TrackStatisticsUpdater();

                lastSkiSubActivity = new SkiSubActivity(skiSubActivity);
                skiSubActivityList.add(lastSkiSubActivity);
            }
            trackStatisticsUpdater.addTrackPoint(trackPoint);

            lastSkiSubActivity.add(trackStatisticsUpdater.getTrackStatistics(), trackPoint);
        }

        return trackPoint != null ? trackPoint.getId() : null;
    }

    public List<SkiSubActivity> getSkiSubActivityList() { return skiSubActivityList; }

    public SkiSubActivity getLastSkiSubActivity() {
        if (!skiSubActivityList.isEmpty()) {
            return skiSubActivityList.get(skiSubActivityList.size() - 1);
        }
        return null;
    }

    public static class SkiSubActivity {
        private TrackStatistics trackStatistics;
        private List<TrackPoint> trackPoints = new ArrayList<>();

        private Duration waitTime = Duration.ofSeconds(0);

        public SkiSubActivity() {
            trackStatistics = new TrackStatistics();
        }

        public SkiSubActivity(SkiSubActivity s) {
            trackStatistics = new TrackStatistics(s.trackStatistics);
            trackPoints.addAll(s.trackPoints);
            waitTime = s.waitTime;
        }

        public Duration getWaitTime() {
            return waitTime;
        }

        public boolean isLift() {
            float gain = trackStatistics.getTotalAltitudeGain() != null ? trackStatistics.getTotalAltitudeGain() : 0f;
            float loss = trackStatistics.getTotalAltitudeLoss() != null ? trackStatistics.getTotalAltitudeLoss() : 0f;
            return gain >= loss;
        }

        public double getSlopePercentage() {
            if (trackStatistics.getTotalDistance().distance_m() == 0) return 0;
            double gain = trackStatistics.getTotalAltitudeGain() != null ? trackStatistics.getTotalAltitudeGain() : 0.0;
            double loss = trackStatistics.getTotalAltitudeLoss() != null ? trackStatistics.getTotalAltitudeLoss() : 0.0;
            return Math.abs(gain - loss) / trackStatistics.getTotalDistance().distance_m() * 100;
        }

        private void add(TrackStatistics trackStatistics, @Nullable TrackPoint lastTrackPoint) {
            set(trackStatistics);
            trackPoints.add(lastTrackPoint);
        }

        private void set(TrackStatistics trackStatistics) {
            this.trackStatistics = trackStatistics;
        }

        public List<TrackPoint> getTrackPoints() {
            return trackPoints;
        }

        public TrackPoint lastTrackPoint() {
            if (trackPoints.isEmpty()) return null;
            return trackPoints.get(trackPoints.size() - 1);
        }
    }
}
