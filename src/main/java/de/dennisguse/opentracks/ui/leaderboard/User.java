package de.dennisguse.opentracks.ui.leaderboard;

public class User {
    private String name;
    private String location;
    private int rank;
    private String verticalMeters;
    private String distance;

    public User(String name, String location, int rank, String verticalMeters, String distance) {
        this.name = name;
        this.location = location;
        this.rank = rank;
        this.verticalMeters = verticalMeters;
        this.distance = distance;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getRank() { return rank; }
    public String getVerticalMeters() { return verticalMeters; }
    public String getDistance() { return distance; }
}