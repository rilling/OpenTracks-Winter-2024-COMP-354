package de.dennisguse.opentracks.ui.leaderboard;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LeaderboardPagerAdapter extends FragmentPagerAdapter {
    private LeaderboardFragment verticalLeaderboardFragment;
    private LeaderboardFragment distanceLeaderboardFragment;
    private LeaderboardFragment dayLeaderboardFragment;

    public LeaderboardPagerAdapter(FragmentManager fm) {
        super(fm);
        verticalLeaderboardFragment = new LeaderboardFragment();
        distanceLeaderboardFragment = new LeaderboardFragment();
        dayLeaderboardFragment = new LeaderboardFragment();
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

