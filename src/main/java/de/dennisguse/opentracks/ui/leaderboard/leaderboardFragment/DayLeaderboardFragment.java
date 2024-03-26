package de.dennisguse.opentracks.ui.leaderboard.leaderboardFragment;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.data.models.Ranking;

public class DayLeaderboardFragment extends LeaderboardFragment {
    private boolean refresh;

    @Override
    protected List<Ranking> getLatestRankingsData() {
        // TODO: Replace the test data with code that gathers the appropriate Ranking data
        List<Ranking> latestRankingsData;
        if (!refresh)
            // Get a smaller data set if this is the first time the rankings data is being collected
            latestRankingsData = getAltTestData();
        else
            latestRankingsData = getTestData();
        // All future rankings data collections should be refreshes
        refresh = true;
        return latestRankingsData;
    }

    private List<Ranking> getTestData() {
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(1, "User 10", "AA",  25));
        rankings.add(new Ranking(2, "User 20", "BB",  24));
        rankings.add(new Ranking(3, "User 30", "CC",  23));
        rankings.add(new Ranking(4,  "User 40", "DD",  22));
        rankings.add(new Ranking(5,  "User 50", "EE",  21));
        rankings.add(new Ranking(6,  "User 60", "FF",  20));
        rankings.add(new Ranking(7,  "User 70", "GG",  19));
        rankings.add(new Ranking(8,  "User 80", "HH",  18));
        rankings.add(new Ranking(9,  "User 90", "II",  17));
        rankings.add(new Ranking(10,  "User 100", "JJ",  16));
        rankings.add(new Ranking(11,  "User 110", "KK",  15));
        rankings.add(new Ranking(12,  "User 120", "LL",  14));
        rankings.add(new Ranking(13,  "User 130", "MM",  13));
        rankings.add(new Ranking(14,  "User 140", "NN",  12));
        rankings.add(new Ranking(15, "User 150", "OO",  11));
        return rankings;
    }

    private List<Ranking> getAltTestData() {
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(1, "Day One", "Steamboat Springs",  25));
        rankings.add(new Ranking(2, "Day Two", "North California CA",  24));
        rankings.add(new Ranking(3, "Day Three", "Steamboat Springs, Color Red",  23));
        rankings.add(new Ranking(4,  "Day Four", "Montreal",  22));
        return rankings;
    }
}