package de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.AbstractActivity;
import de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats.Metric;
import de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats.Frequency;
import de.dennisguse.opentracks.databinding.DailyStatsBinding;

public class DailyStatsActivity extends AbstractActivity {

    private DailyStatsBinding viewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_stats);

        // Create and populate the spin_metrics spinner with Metric enums.
        Spinner spin_metrics = (Spinner)findViewById(R.id.daily_metric);
        ArrayAdapter<Metric> array_metrics = new ArrayAdapter<Metric>(this, android.R.layout.simple_spinner_item, Metric.values());
        spin_metrics.setAdapter(array_metrics);

        // Create and populate the spin_freq spinner with Metric enums.
        Spinner spin_freq = (Spinner)findViewById(R.id.daily_data_point);
        ArrayAdapter<Frequency> array_freq = new ArrayAdapter<Frequency>(this, android.R.layout.simple_spinner_item, Frequency.values());
        spin_freq.setAdapter(array_freq);

        // Create event listening for spin_metrics.
        spin_metrics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Metric selected = Metric.values()[pos];
                //Log.d("DAILY_STATS", "ENUM: \"" + selected.toString() + "\" selected.");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Create event listening for spin_freq.
        spin_freq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Frequency selected = Frequency.values()[pos];
                //Log.d("DAILY_STATS", "ENUM: \"" + selected.toString() + "\" selected.");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        setSupportActionBar(viewBinding.bottomAppBarLayout.bottomAppBar);
    }

    @Override
    protected View getRootView() {
        viewBinding = DailyStatsBinding.inflate(getLayoutInflater());
        return viewBinding.getRoot();
    }
}