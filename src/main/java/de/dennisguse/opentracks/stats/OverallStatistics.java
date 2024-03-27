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


import de.dennisguse.opentracks.data.models.Distance;
import de.dennisguse.opentracks.data.models.Speed;
import de.dennisguse.opentracks.data.models.Track;
import de.dennisguse.opentracks.stats.TrackStatistics;


public class OverallStatistics {

    // Static field that stores singleton instance
    private static OverallStatistics instance;

    protected OverallStatistics() {
        this.totalRunsOverall = 50;
        this.totalSkiDaysOverall = 25;
        this.totalTrackDistanceOverall = Distance.ofKilometer(500);
        this.totalVerticalDescentOverall = Distance.ofMM(100);
        this.avgSpeedOverall = Speed.ofKMH(40);
        this.slopePercentageOverall = .66;
    }

    protected OverallStatistics(int totalRunsOverall,
                             int totalSkiDaysOverall,
                             Distance totalTrackDistanceOverall,
                             Distance totalVerticalDescentOverall,
                             Speed avgSpeedOverall,
                             double slopePercentageOverall) {
        this.totalRunsOverall = totalRunsOverall;
        this.totalSkiDaysOverall = totalSkiDaysOverall;
        this.totalTrackDistanceOverall = totalTrackDistanceOverall;
        this.totalVerticalDescentOverall = totalVerticalDescentOverall;
        this.avgSpeedOverall = avgSpeedOverall;
        this.slopePercentageOverall = slopePercentageOverall;
    }

    // Static method that controls access to singleton instance
    public static OverallStatistics getInstance(){
        if (instance == null){
            instance = new OverallStatistics();
        }
        return instance;
    }
    
    // Static method that controls access to singleton instance with parameters
    public static OverallStatistics getInstance(int totalRunsOverall, int totalSkiDaysOverall, Distance totalTrackDistanceOverall, Distance totalVerticalDescentOverall, Speed avgSpeedOverall, double slopePercentageOverall){
        if (instance == null){
            instance = new OverallStatistics(totalRunsOverall, totalSkiDaysOverall, totalTrackDistanceOverall, totalVerticalDescentOverall, avgSpeedOverall, slopePercentageOverall);
        }
        return instance;
    }

    // The total number of all-time runs.
    private int totalRunsOverall;

    // The total number of all-time days skied.
    private int totalSkiDaysOverall;

    // The total all-time distance skied (consider all tracks).
    private Distance totalTrackDistanceOverall;

    // The total all-time distance covered while in vertical descents.
    private Distance totalVerticalDescentOverall;

    // The average all-time speed.
    private Speed avgSpeedOverall;

    // The all-time slope percentage.
    private double slopePercentageOverall;



    public int getTotalRunsOverall(){
        return this.totalRunsOverall;
    }

    public void setTotalRunsOverall(int totalRunsOverall){
        this.totalRunsOverall = totalRunsOverall;
    }


    public int getTotalSkiDaysOverall(){
        return this.totalSkiDaysOverall;
    }

    public void setTotalSkiDaysOverall(int totalSkiDaysOverall){
        this.totalSkiDaysOverall = totalSkiDaysOverall;
    }


    public Distance getTotalTrackDistanceOverall(){
        return this.totalTrackDistanceOverall;
    }

    public void setTotalTrackDistanceOverall(Distance totalTrackDistanceOverall){
        this.totalTrackDistanceOverall = totalTrackDistanceOverall;
    }


    public Distance getTotalVerticalDescentOverall(){
        return this.totalVerticalDescentOverall;
    }

    public void setTotalVerticalDescentOverall(Distance totalVerticalDescentOverall){
        this.totalVerticalDescentOverall = totalVerticalDescentOverall;
    }


    public Speed getAvgSpeedOverall(){
        return this.avgSpeedOverall;
    }

    public void setAvgSpeedOverall(Speed avgSpeedOverall){
        this.avgSpeedOverall = avgSpeedOverall;
    }


    public double getSlopePercentageOverall(){
        return this.slopePercentageOverall;
    }

    public void setSlopePercentageOverall(double slopePercentageOverall){
        this.slopePercentageOverall = slopePercentageOverall;
    }


    //The addToOverallStatistics() method is called whenever updating the overall
    //statistics of a user.
    public void addToOverallStatistics(TrackStatistics trackStatistics){
        calculateTotalRunsOverall(trackStatistics);
        calculateTotalSkiDaysOverall(trackStatistics);
        calculateTotalTrackDistanceOverall(trackStatistics);
        calculateTotalVerticalDescentOverall(trackStatistics);
        calculateAvgSpeedOverall(trackStatistics);
        calculateSlopePercentageOverall(trackStatistics);

    }
    private void calculateTotalRunsOverall(TrackStatistics trackStatistics){
        totalRunsOverall += trackStatistics.getTotalRunsSeason();
    }

    private void calculateTotalSkiDaysOverall(TrackStatistics trackStatistics){
        totalSkiDaysOverall += trackStatistics.getTotalSkiDaysSeason();
    }

    private void calculateTotalTrackDistanceOverall(TrackStatistics trackStatistics){
        totalTrackDistanceOverall.plus(trackStatistics.getTotalTrackDistanceSeason());
    }

    private void calculateTotalVerticalDescentOverall(TrackStatistics trackStatistics){
        totalVerticalDescentOverall.plus(trackStatistics.getTotalVerticalDescentSeasonSeason());
    }

    private void calculateAvgSpeedOverall(TrackStatistics trackStatistics){
        double tempAvgOverall = (Double.parseDouble(String.valueOf(avgSpeedOverall)) + Double.parseDouble(String.valueOf(trackStatistics.getAvgSpeedSeason())))/2;
        avgSpeedOverall = new Speed(tempAvgOverall);
    }

    private void calculateSlopePercentageOverall(TrackStatistics trackStatistics){
        slopePercentageOverall = (slopePercentageOverall + trackStatistics.getSlopePercentageSeason())/2;

    }
}
