package de.dennisguse.opentracks.ui.leaderboard;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LeaderboardPagerAdapter extends FragmentPagerAdapter {

    public LeaderboardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // Return the appropriate Fragment for each tab position
        switch (position) {
            case 0:
                return new VerticalLeaderboardFragment();
            case 1:
                return new DistanceLeaderboardFragment(); // You will need to create this Fragment
            case 2:
                return new DayLeaderboardFragment(); // You will need to create this Fragment
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // We have 3 tabs
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Vertical";
            case 1:
                return "Distance";
            case 2:
                return "Day";
            default:
                return null;
        }
    }
}

