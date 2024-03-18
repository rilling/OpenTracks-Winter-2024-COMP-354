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
        for (int i = 1; i <= 25; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(Ranking item) {
        RANKINGS.add(item);
        RANKING_MAP.put(item.rank, item);
    }

    private static Ranking createPlaceholderItem(int position) {
        return new Ranking(1, null, null, null,  5);
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