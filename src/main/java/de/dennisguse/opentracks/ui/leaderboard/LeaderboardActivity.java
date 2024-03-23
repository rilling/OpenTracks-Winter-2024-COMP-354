package de.dennisguse.opentracks.ui.leaderboard;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import de.dennisguse.opentracks.R;

import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import de.dennisguse.opentracks.ui.leaderboard.SectionsPagerAdapter;
import de.dennisguse.opentracks.databinding.ActivityLeaderboardBinding;
import de.dennisguse.opentracks.ui.leaderboard.leaderboardList.RankingsRecyclerViewAdapter;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView leaderboardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboardList = findViewById(R.id.leaderboardList);

        // Set up the RecyclerView with the adapter
        // Assuming RankingsRecyclerViewAdapter is already implemented
        RankingsRecyclerViewAdapter adapter = new RankingsRecyclerViewAdapter(RankingsRecyclerViewAdapter.makePlaceholderRankingList());
        leaderboardList.setAdapter(adapter);

        // Set up the layout manager
        leaderboardList.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Set up toggle buttons and filter button listeners
    }

    // TODO: Implement methods to handle toggle and filter actions
}