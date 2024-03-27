package de.dennisguse.opentracks;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import de.dennisguse.opentracks.databinding.ActivitySeasonalBinding;
import de.dennisguse.opentracks.ui.aggregatedStatistics.StatisticsActivity;


/**
 * This view will allow users to see the list of each season and select the season they wish to see the aggregated data for.
 *
 * @author Woo Jun Ann, Zachary Therrien
 * */
public class SeasonalActivity extends AbstractActivity {
    private ActivitySeasonalBinding viewBinding;
    private RecyclerView seasonsRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Seasonal Activity!");
        seasonsRecyclerView = findViewById(R.id.seasons_recyclerView);

        GoToIndividualSite(findViewById(R.id.activity_game_answer1_btn));
        GoToIndividualSite(findViewById(R.id.activity_game_answer2_btn));
        GoToAllTimeStats(findViewById(R.id.activity_game_answer3_btn));

        setSupportActionBar(viewBinding.bottomAppBarLayout.bottomAppBar);
    }

    private void GoToIndividualSite(Button button)
    {
        button.setOnClickListener(view ->
        {
            Intent intent = new Intent(SeasonalActivity.this, SeasonalActivityPerSeason.class);
            intent.putExtra("seasonTitle", button.getText());
            startActivity(intent);
        });
    }

    private void GoToAllTimeStats(Button button) {
        button.setOnClickListener(view ->
        {
            Intent intent = new Intent(SeasonalActivity.this, StatisticsActivity.class);
            intent.putExtra("seasonTitle", button.getText());
            startActivity(intent);
        });
    }

    @Override
    protected View getRootView()
    {
        viewBinding = ActivitySeasonalBinding.inflate(getLayoutInflater());
        return viewBinding.getRoot();
    }
}
