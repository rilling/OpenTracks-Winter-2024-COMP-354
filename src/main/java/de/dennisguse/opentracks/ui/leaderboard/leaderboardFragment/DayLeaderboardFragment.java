package de.dennisguse.opentracks.ui.leaderboard.leaderboardFragment;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.data.models.Ranking;

public class DayLeaderboardFragment extends LeaderboardFragment {

    @Override
    public void refreshRankingsData() {
        // TODO: Replace the test data with code that gathers the appropriate Ranking data
        setLeaderboardAdapterRankingList(getTestData());
    }

    private List<Ranking> getTestData() {
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(1, "Day One", "Steamboat Springs",  25));
        rankings.add(new Ranking(2, "Day Two", "North California CA",  24));
        rankings.add(new Ranking(3, "Day Three", "Steamboat Springs, Color Red",  23));
        rankings.add(new Ranking(4,  "Day Four", "Montreal",  22));
        return rankings;
    }
}
