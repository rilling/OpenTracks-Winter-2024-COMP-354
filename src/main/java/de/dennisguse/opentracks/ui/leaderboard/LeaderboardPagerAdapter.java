package de.dennisguse.opentracks.ui.leaderboard;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import de.dennisguse.opentracks.ui.leaderboard.leaderboardFragment.DayLeaderboardFragment;
import de.dennisguse.opentracks.ui.leaderboard.leaderboardFragment.DistanceLeaderboardFragment;
import de.dennisguse.opentracks.ui.leaderboard.leaderboardFragment.VerticalLeaderboardFragment;

public class LeaderboardPagerAdapter extends FragmentPagerAdapter {
    private VerticalLeaderboardFragment verticalLeaderboardFragment;
    private DistanceLeaderboardFragment distanceLeaderboardFragment;
    private DayLeaderboardFragment dayLeaderboardFragment;

    public LeaderboardPagerAdapter(FragmentManager fm) {
        super(fm);
        verticalLeaderboardFragment = new VerticalLeaderboardFragment();
        distanceLeaderboardFragment = new DistanceLeaderboardFragment();
        dayLeaderboardFragment = new DayLeaderboardFragment();
    }

    public enum LeaderboardType {
        Vertical(0),
        Distance(1),
        Day(2);

        // Assisted by https://stackoverflow.com/questions/1067352/can-i-set-enum-start-value-in-java
        private final int value;

        private LeaderboardType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Override
    public int getCount() {
        return LeaderboardType.values().length;
    }

    @Override
    public Fragment getItem(int position) {
        // Return the appropriate Fragment for each tab position
        if (position == LeaderboardType.Vertical.value)
            return verticalLeaderboardFragment;
        else if (position == LeaderboardType.Distance.value)
            return distanceLeaderboardFragment;
        else if (position == LeaderboardType.Day.value)
            return dayLeaderboardFragment;
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == LeaderboardType.Vertical.value)
            return LeaderboardType.Vertical.name();
        else if (position == LeaderboardType.Distance.value)
            return LeaderboardType.Distance.name();
        else if (position == LeaderboardType.Day.value)
            return LeaderboardType.Day.name();
        return null;
    }
}

