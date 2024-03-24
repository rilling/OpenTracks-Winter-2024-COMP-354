package de.dennisguse.opentracks;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import de.dennisguse.opentracks.databinding.ActivitySeasonalBinding;


/**
 * This view will allow users to see the list of each season and select the season they wish to see the aggregated data for.
 *
 * @author Woo Jun Ann, Zachary Therrien
 * */
public class SeasonalActivity extends AbstractActivity {

    private ActivitySeasonalBinding viewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sesonal Activity!");


        setSupportActionBar(viewBinding.bottomAppBarLayout.bottomAppBar);
    }

    @Override
    protected View getRootView() {
        viewBinding = ActivitySeasonalBinding.inflate(getLayoutInflater());
        return viewBinding.getRoot();
    }
}
