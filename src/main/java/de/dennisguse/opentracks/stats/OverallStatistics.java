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

public class OverallStatistics {

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

}
