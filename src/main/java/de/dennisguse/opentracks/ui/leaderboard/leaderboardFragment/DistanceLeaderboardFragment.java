package de.dennisguse.opentracks.ui.leaderboard.leaderboardFragment;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.data.models.Ranking;

public class DistanceLeaderboardFragment extends LeaderboardFragment {

    @Override
    protected void refreshRankingsData() {
        // TODO: Replace the test data with code that gathers the appropriate Ranking data
        setLeaderboardAdapterRankingList(getTestData());
    }

    private List<Ranking> getTestData() {
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
