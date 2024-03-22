package de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats;

import android.os.Bundle;
import android.view.View;

import de.dennisguse.opentracks.AbstractActivity;
import de.dennisguse.opentracks.databinding.DailyStatsBinding;

public class DailyStatsActivity extends AbstractActivity {

    private DailyStatsBinding viewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setSupportActionBar(viewBinding.bottomAppBarLayout.bottomAppBar);
    }

    @Override
    protected View getRootView() {
        viewBinding = DailyStatsBinding.inflate(getLayoutInflater());
        return viewBinding.getRoot();
    }
}