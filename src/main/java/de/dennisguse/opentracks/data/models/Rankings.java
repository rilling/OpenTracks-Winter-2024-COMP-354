package de.dennisguse.opentracks.data.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class for the Rankings displayed on a Leaderboard.
 */
public class Rankings {

    /**
     * An array of sample Rankings.
     */
    public static final List<Ranking> RANKINGS = new ArrayList<Ranking>();

    /**
     * A map of sample Rankings, by rank.
     */
    public static final Map<Integer, Ranking> RANKING_MAP = new HashMap<Integer, Ranking>();

    static {
        // Add some sample Rankings, until we can get refresh working.
        addItem(new Ranking(1, null, "Da bes", "Steamboat Springs",  25));
        addItem(new Ranking(2, null, "Second place", "North California CA",  24));
        addItem(new Ranking(3, null, "Tertiary", "Steamboat Springs, Color Red",  23));
        addItem(new Ranking(4, null, "Quad Runner", "Montreal",  22));
        addItem(new Ranking(5, null, "Quintuple champ", "Steamboat Springs",  21));
        addItem(new Ranking(6, null, "Gang of Six", "Montreal",  20));
        addItem(new Ranking(7, null, "Seven", "Steamboat Springs",  19));
        addItem(new Ranking(8, null, "Eight", "Montreal",  18));
        addItem(new Ranking(9, null, "Ninth", "Montreal",  17));
        addItem(new Ranking(10, null, "DoubleDigits", "Steamboat Springs",  16));
        addItem(new Ranking(11, null, "El e Ven", "Montreal",  15));
        addItem(new Ranking(12, null, "Twelve", "Montreal",  14));
        addItem(new Ranking(13, null, "XIII", "Montreal",  13));
        addItem(new Ranking(14, null, "Four Teen", "Steamboat Springs",  12));
        addItem(new Ranking(15, null, "Fif Teen", "Montreal",  11));
        addItem(new Ranking(16, null, "Six Teen", "Steamboat Springs",  10));
        addItem(new Ranking(17, null, "Seven+10", "Montreal",  9));
        addItem(new Ranking(18, null, "Eight een", "Montreal",  8));
        addItem(new Ranking(19, null, "Nineteen", "Steamboat Springs",  7));
        addItem(new Ranking(20, null, "10+10", "Steamboat Springs",  6));
        addItem(new Ranking(21, null, "Twenty-first", "Montreal",  5));
        addItem(new Ranking(22, null, "TwoTwo", "Montreal",  4));
        addItem(new Ranking(23, null, "TwoThree", "Steamboat Springs",  3));
        addItem(new Ranking(24, null, "The day", "Montreal",  2));
        addItem(new Ranking(25, null, "The saved day", "Montreal",  1));
    }

    private static void addItem(Ranking item) {
        RANKINGS.add(item);
        RANKING_MAP.put(item.rank, item);
    }

    /**
     * An individual user's Ranking on a Leaderboard, designed to work with any type of Leaderboard.
     */
    public static class Ranking {
        public final int rank;
        public final Bitmap profilePicture;
        public final String username;
        public final String location;
        public final double score;

        public Ranking(int rank, Bitmap profilePicture, String username, String location, double score) {
            this.rank = rank;
            this.profilePicture = profilePicture;
            this.username = username;
            this.location = location;
            this.score = score;
        }
    }
}