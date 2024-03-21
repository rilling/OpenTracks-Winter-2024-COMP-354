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

public class SeasonalStatistics {

    // The total number of runs in a season.
    private int totalRunsSeason;

    // The total number of days skied in a season.
    private int totalSkiDaysSeason;

    // The total distance skied (consider all tracks) in a season.
    private Distance totalTrackDistanceSeason;

    // The total distance covered while in vertical descents in a season.
    private Distance totalVerticalDescentSeason;

    // The average speed in a season.
    private Speed avgSpeedSeason;

    // The slope percentage in a season.
    private double slopePercentageSeason;



    public int getTotalRunsSeason(){
        return this.totalRunsSeason;
    }

    public void setTotalRunsSeason(int totalRunsSeason){
        this.totalRunsSeason = totalRunsSeason;
    }


    public int getTotalSkiDaysSeason(){
        return this.totalSkiDaysSeason;
    }

    public void setTotalSkiDaysSeason(int totalSkiDaysSeason){
        this.totalSkiDaysSeason = totalSkiDaysSeason;
    }


    public Distance getTotalTrackDistanceSeason(){
        return this.totalTrackDistanceSeason;
    }

    public void setTotalTrackDistanceSeason(Distance totalTrackDistanceSeason){
        this.totalTrackDistanceSeason = totalTrackDistanceSeason;
    }


    public Distance getTotalVerticalDescentSeasonSeason(){
        return this.totalVerticalDescentSeason;
    }

    public void setTotalVerticalDescentSeason(Distance totalVerticalDescentSeason){
        this.totalVerticalDescentSeason = totalVerticalDescentSeason;
    }


    public Speed getAvgSpeedSeason(){
        return this.avgSpeedSeason;
    }

    public void setAvgSpeedSeason(Speed avgSpeedSeason){
        this.avgSpeedSeason = avgSpeedSeason;
    }


    public double getSlopePercentageSeason(){
        return this.slopePercentageSeason;
    }

    public void setSlopePercentageSeason(double slopePercentageSeason){
        this.slopePercentageSeason = slopePercentageSeason;
    }

}
