package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.stats.MockupData;

public class StatisticsPreviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_preview);

        MockupData mockupData1 = new MockupData(2022); // example 2022-2023
        MockupData mockupData2 = new MockupData(2023); // example 2023-2024

        List<MockupData> seasons = new ArrayList<>();

        RecyclerView previews = findViewById(R.id.seasonPreviews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        previews.setLayoutManager(linearLayoutManager);

        StatisticsPreviewAdapter previewAdapter = new StatisticsPreviewAdapter(seasons);
        previews.setAdapter(previewAdapter);

        seasons.add(mockupData1);
        seasons.add(mockupData2);

        previewAdapter.notifyDataSetChanged();

    }
}
