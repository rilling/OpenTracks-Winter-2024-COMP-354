package de.dennisguse.opentracks.ui.leaderboard.leaderboardFragment;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.data.models.Ranking;

public class DistanceLeaderboardFragment extends LeaderboardFragment {
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
        rankings.add(new Ranking(1, "User 1", "A",  25));
        rankings.add(new Ranking(2, "User 2", "B",  24));
        rankings.add(new Ranking(3, "User 3", "C",  23));
        rankings.add(new Ranking(4,  "User 4", "D",  22));
        rankings.add(new Ranking(5,  "User 5", "E",  21));
        rankings.add(new Ranking(6,  "User 6", "F",  20));
        rankings.add(new Ranking(7,  "User 7", "G",  19));
        rankings.add(new Ranking(8,  "User 8", "H",  18));
        rankings.add(new Ranking(9,  "User 9", "I",  17));
        rankings.add(new Ranking(10,  "User 10", "J",  16));
        rankings.add(new Ranking(11,  "User 11", "K",  15));
        rankings.add(new Ranking(12,  "User 12", "L",  14));
        rankings.add(new Ranking(13,  "User 13", "M",  13));
        rankings.add(new Ranking(14,  "User 14", "N",  12));
        rankings.add(new Ranking(15, "User 15", "O",  11));
        return rankings;
    }


    private List<Ranking> getAltTestData() {
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(1, "First", "Steamboat Springs",  25));
        rankings.add(new Ranking(2, "Second", "North California CA",  24));
        rankings.add(new Ranking(3, "Third", "Steamboat Springs, Color Red",  23));
        rankings.add(new Ranking(4,  "Fourth", "Montreal",  22));
        rankings.add(new Ranking(5,  "Fifth", "Steamboat Springs",  21));
        rankings.add(new Ranking(6,  "Sixth", "Montreal",  20));
        rankings.add(new Ranking(7,  "Seven", "Steamboat Springs",  19));
        return rankings;
    }
}
