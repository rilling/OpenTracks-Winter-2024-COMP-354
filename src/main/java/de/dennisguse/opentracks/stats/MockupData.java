package de.dennisguse.opentracks.stats;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import de.dennisguse.opentracks.data.models.*;

public class MockupData implements Serializable {
    private int season;
    public MockupData(int season){
        this.season = season;
    }

    public MockupData(){
        this.season = 0;
    }

    public int getSeason() {
        return season;
    }

    public List<TrackStatistics> getTrackStatistics() {
        List<TrackStatistics> trackStatisticsArray = new ArrayList<>();
        trackStatisticsArray.add(createTrackStatistics(1000, 100, 10, 100, 10, 10, 100, 100, 10, 10, 100, 10, 100, 10, 10, false));
        trackStatisticsArray.add(createTrackStatistics(2000, 200, 20, 200, 20, 20, 200, 200, 20, 20, 200, 20, 200, 20, 20, true));

        return trackStatisticsArray;
    }

    private TrackStatistics createTrackStatistics(
            double totalDistanceValue,long totalTime,double maxSpeedValue,double maxAltitudeValue,float totalAltLossValue,
            float totalAltGainValue,long totalMovingTimeValue, int heartRate, long avgSpeedSeason, double slopePercentageSeason,
            double totalVerticalDescentSeasonValue,int totalRunsSeasonValue, int totalSkiDaysSeasonValue,
            double totalTrackingDistance,double minAltitude,boolean isIdle) {

        TrackStatistics trackStatistics = new TrackStatistics();
        trackStatistics.setTotalDistance(Distance.of(totalDistanceValue));
        trackStatistics.setTotalTime(Duration.ofSeconds(totalTime));
        trackStatistics.setMaxSpeed(new Speed(maxSpeedValue));
        trackStatistics.setMaxAltitude(maxAltitudeValue);
        trackStatistics.setTotalAltitudeLoss(totalAltLossValue);
        trackStatistics.setTotalAltitudeGain(totalAltGainValue);
        trackStatistics.setMovingTime(Duration.ofSeconds(totalMovingTimeValue));
        trackStatistics.setAverageHeartRate(new HeartRate(heartRate));
        trackStatistics.setAvgSpeedSeason(new Speed(avgSpeedSeason));
        trackStatistics.setSlopePercentageSeason(slopePercentageSeason);
        trackStatistics.setStartTime(Instant.now());
        trackStatistics.setStopTime(Instant.now());
        trackStatistics.setTotalVerticalDescentSeason(Distance.of(totalVerticalDescentSeasonValue));
        trackStatistics.setTotalRunsSeason(totalRunsSeasonValue);
        trackStatistics.setTotalSkiDaysSeason(totalSkiDaysSeasonValue);
        trackStatistics.setTotalTrackDistanceSeason(Distance.of(totalTrackingDistance));
        trackStatistics.setMinAltitude(minAltitude);
        trackStatistics.setIdle(isIdle);

        return trackStatistics;
    }
}
