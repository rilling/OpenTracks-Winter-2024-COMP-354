package de.dennisguse.opentracks.data.models;

import android.graphics.Bitmap;

/**
 * An individual user's Ranking on a specific Leaderboard.
 * This class is designed to work with any type of Leaderboard;
 * as such, the Score attribute is meant to represent any numerical value that a leaderboard would be based on.
 */
public class Ranking {
    private int rank;
    private String username;
    private String location;
    private double score;
    private Bitmap profilePicture;

    // TODO: Consider deleting this constructor once issue 67 is implemented (and we have no more need for placeholder Ranking instances)
    public Ranking(int rank, String username, String location, double score) {
        setRank(rank);
        setUsername(username);
        setLocation(location);
        setScore(score);
    }

    // The full constructor for a Ranking instance.
    public Ranking(int rank, String username, String location, double score, Bitmap profilePicture) {
        setRank(rank);
        setUsername(username);
        setLocation(location);
        setScore(score);
        setProfilePicture(profilePicture);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    // Getter and Setter for 'username'
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }
}