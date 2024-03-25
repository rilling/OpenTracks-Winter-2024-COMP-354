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
    private final List<SkiSubActivity> skiActivityList;

    private double waitThreshold = 1.5;
    private int thresholdCount = 3;

    private Queue<TrackPoint> trackPointQueue = new LinkedList<>();

    private RunLiftStatistics.SkiSubActivity skiSubActivity, lastSkiSubActivity;

    public RunLiftStatistics() {
        skiSubActivity = new SkiSubActivity();
        lastSkiSubActivity = new SkiSubActivity();

        skiActivityList = new ArrayList<>();
        skiActivityList.add(lastSkiSubActivity);
    }

    public TrackPoint.Id addTrackPoints(TrackPointIterator trackPointIterator) {
        boolean newSkiSubActivityAdded = false;
        TrackPoint trackPoint = null;

        while (trackPointIterator.hasNext()) {
            trackPoint = trackPointIterator.next();



            trackStatisticsUpdater = new TrackStatisticsUpdater();
            trackStatisticsUpdater.addTrackPoint(trackPoint);



        }

        return trackPoint != null ? trackPoint.getId() : null;
    }

    public List<SkiSubActivity> getSkiActivityList() { return skiActivityList; }

    public SkiSubActivity getLastSkiSubActivity() {
        if (!skiActivityList.isEmpty()) {
            return skiActivityList.get(skiActivityList.size() - 1);
        }
        return null;
    }

    public static class SkiSubActivity {
        private TrackStatistics trackStatistics;
        private List<TrackPoint> trackPoints;

        private Duration waitTime = Duration.ofSeconds(0);

        public SkiSubActivity() {
            trackStatistics = new TrackStatistics();
            trackPoints = new ArrayList<>();
        }

        public SkiSubActivity(SkiSubActivity s) {
            trackStatistics = new TrackStatistics(s.trackStatistics);
            trackPoints.addAll(s.trackPoints);
        }

        public Duration getWaitTime() {
            return waitTime;
        }

        public double getSlopePercentage() {
            if (trackStatistics.getTotalDistance().distance_m() == 0) return 0;
            double gain = trackStatistics.getTotalAltitudeGain() != null ? trackStatistics.getTotalAltitudeGain() : 0.0;
            double loss = trackStatistics.getTotalAltitudeLoss() != null ? trackStatistics.getTotalAltitudeLoss() : 0.0;
            return Math.abs(gain - loss) / trackStatistics.getTotalDistance().distance_m() * 100;
        }

        private void add(TrackStatistics trackStatistics, @Nullable TrackPoint lastTrackPoint) {
//            distance = distance.plus(trackStatistics.getTotalDistance());
//            time = time.plus(trackStatistics.getTotalTime());
//            gain_m = trackStatistics.hasTotalAltitudeGain() ? trackStatistics.getTotalAltitudeGain() : gain_m;
//            loss_m = trackStatistics.hasTotalAltitudeLoss() ? trackStatistics.getTotalAltitudeLoss() : loss_m;
//            avgHeartRate = trackStatistics.getAverageHeartRate();
//            if (lastTrackPoint == null) {
//                return;
//            }
//            if (hasGain() && lastTrackPoint.hasAltitudeGain()) {
//                gain_m = gain_m - lastTrackPoint.getAltitudeGain();
//            }
//            if (hasLoss() && lastTrackPoint.hasAltitudeLoss()) {
//                loss_m = loss_m - lastTrackPoint.getAltitudeLoss();
//            }
        }

        private void set(TrackStatistics trackStatistics) {
//            distance = trackStatistics.getTotalDistance();
//            time = trackStatistics.getTotalTime();
//            gain_m = trackStatistics.hasTotalAltitudeGain() ? trackStatistics.getTotalAltitudeGain() : gain_m;
//            loss_m = trackStatistics.hasTotalAltitudeLoss() ? trackStatistics.getTotalAltitudeLoss() : loss_m;
//            avgHeartRate = trackStatistics.getAverageHeartRate();
        }
    }
}
