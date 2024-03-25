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

    private final double waitThreshold = 1.6;
    private final double runThreshold = 3.5;
    private int thresholdCount = 3;

    private final Queue<TrackPoint> waitQueue = new LinkedList<>();

    private RunLiftStatistics.SkiSubActivity skiSubActivity, lastSkiSubActivity;

    public RunLiftStatistics() {
        skiSubActivity = new SkiSubActivity();
        lastSkiSubActivity = new SkiSubActivity();

        skiSubActivityList = new ArrayList<>();
        skiSubActivityList.add(lastSkiSubActivity);
    }

    private boolean isWaiting(TrackPoint trackPoint) {
        return trackPoint.getSpeed().speed_mps() <= waitThreshold;
    }

    private boolean isRun(TrackPoint trackPoint) {
        return trackPoint.getAltitudeLoss() - trackPoint.getAltitudeGain() >= 0.0
                && trackPoint.getSpeed().speed_mps() >= runThreshold;
    }

    public TrackPoint.Id addTrackPoints(TrackPointIterator trackPointIterator) {
        boolean newSkiSubActivityAdded = false;
        TrackPoint trackPoint = null;
        TrackPoint lastTrackPoint = null;

        while (trackPointIterator.hasNext()) {
            trackPoint = trackPointIterator.next();

            if (isWaiting(trackPoint)) {
                waitQueue.add(trackPoint);
            } else {
                if (lastTrackPoint != null && lastTrackPoint.getAltitudeLoss() != trackPoint.getAltitudeLoss()) {
                    skiSubActivity = new SkiSubActivity();

                    trackStatisticsUpdater = new TrackStatisticsUpdater();

                    lastSkiSubActivity = new SkiSubActivity(skiSubActivity);
                    skiSubActivityList.add(lastSkiSubActivity);
                }

                if (!waitQueue.isEmpty()) {
                    if (lastTrackPoint == null || !(isRun(lastTrackPoint) == isRun(trackPoint))) {
                        if (lastTrackPoint == null) lastTrackPoint = waitQueue.peek();
                        lastSkiSubActivity.setWaitTime(Duration.between(lastTrackPoint.getTime(), trackPoint.getTime()));
                    }
                }

                while (!waitQueue.isEmpty()) {
                    trackStatisticsUpdater.addTrackPoint(waitQueue.remove());
                    lastSkiSubActivity.add(trackStatisticsUpdater.getTrackStatistics(), trackPoint);
                }
                trackStatisticsUpdater.addTrackPoint(trackPoint);

                lastSkiSubActivity.add(trackStatisticsUpdater.getTrackStatistics(), trackPoint);
                lastTrackPoint = trackPoint;
            }
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
        private final List<TrackPoint> trackPoints = new ArrayList<>();

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

        public boolean isNew() { return trackPoints.isEmpty(); }

        public TrackPoint lastTrackPoint() {
            if (trackPoints.isEmpty()) return null;
            return trackPoints.get(trackPoints.size() - 1);
        }

        public void setWaitTime(Duration time) {
            waitTime = time;
        }
    }
}
