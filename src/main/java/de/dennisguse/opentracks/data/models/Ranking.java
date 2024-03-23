package de.dennisguse.opentracks.data.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An individual user's Ranking on a Leaderboard, designed to work with any type of Leaderboard.
 */
public class Ranking {
    public int rank;
    public String username;
    public String location;
    public double score;
    public Bitmap profilePicture;

    // TODO: Consider deleting this constructor once Refresh is implemented (and we have no more need for placeholder Ranking instances)
    public Ranking(int rank, String username, String location, double score) {
        this.rank = rank;
        this.username = username;
        this.location = location;
        this.score = score;
    }

    // The full constructor for a Ranking instance.
    public Ranking(int rank, String username, String location, double score, Bitmap profilePicture) {
        this.rank = rank;
        this.username = username;
        this.location = location;
        this.score = score;
    }
}