package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.stats.MockupData;
import de.dennisguse.opentracks.stats.TrackStatistics;

public class StatisticsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);

        MockupData mockupData =  (MockupData) getIntent().getSerializableExtra("data");
        List<TrackStatistics> trackStatistics = mockupData.getTrackStatistics();
        TrackStatistics summary = TrackStatistics.sumOfTotalStats(trackStatistics);
        TextView totalDistance = findViewById(R.id.totalTrackDistId);
        TextView totalVert = findViewById(R.id.totalVertId);
        TextView skiDays = findViewById(R.id.skiDaysId);
        TextView totalRuns = findViewById(R.id.totalRunsId);
        TextView avgSpeed = findViewById(R.id.avgSpeedId);
        TextView avgSlp = findViewById(R.id.avgSlopeId);

        totalDistance.setText((summary.getTotalTrackDistanceSeason().toM()) + "M");
        totalVert.setText(summary.getTotalVerticalDescentSeasonSeason().toM() + "M");
        skiDays.setText(String.valueOf(summary.getTotalSkiDaysSeason()));
        totalRuns.setText(String.valueOf(summary.getTotalRunsSeason()));
        avgSpeed.setText(summary.getAvgSpeedSeason().toKMH() + "KMH");
        avgSlp.setText((summary.getSlopePercentageSeason() + "%"));




    }


}
