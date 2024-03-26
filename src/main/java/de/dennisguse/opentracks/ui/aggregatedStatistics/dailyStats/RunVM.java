package de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats;

/**
 * A view model representing a run for visualizing daily statistics.
 */
public class RunVM {
    private int id;
    private float avgSpeed;
    private float avgSlope;
    private float totalDistance;
    private float chairliftSpeed;

    public RunVM() {}

    public RunVM(int id, float avgSpeed, float avgSlope, float chairliftSpeed, float totalDistance) {
        this.id = id;
        this.avgSpeed = avgSpeed;
        this.avgSlope = avgSlope;
        this.chairliftSpeed = chairliftSpeed;
        this.totalDistance = totalDistance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public float getAvgSlope() {
        return avgSlope;
    }

    public void setAvgSlope(float avgSlope) {
        this.avgSlope = avgSlope;
    }

    public float getChairliftSpeed() {
        return chairliftSpeed;
    }

    public void setChairliftSpeed(float chairliftSpeed) {
        this.chairliftSpeed = chairliftSpeed;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }
}
