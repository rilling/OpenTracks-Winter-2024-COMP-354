package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import de.dennisguse.opentracks.data.ContentProviderUtils;
import de.dennisguse.opentracks.data.TrackSelection;
import de.dennisguse.opentracks.data.models.Track;

public class AggregatedStatisticsModel extends AndroidViewModel {

    private MutableLiveData<AggregatedStatistics> aggregatedStats;
    private MutableLiveData<AggregatedStatistics> aggregatedDayStats;

    public AggregatedStatisticsModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<AggregatedStatistics> getAggregatedStats(@Nullable TrackSelection selection) {
        if (aggregatedStats == null) {
            aggregatedStats = new MutableLiveData<>();
            loadAggregatedStats(selection);
        }
        return aggregatedStats;
    }

    public LiveData<AggregatedStatistics> getAggregatedDailyStats(@Nullable TrackSelection selection) {
        if (aggregatedDayStats == null) {
            aggregatedDayStats = new MutableLiveData<>();
            loadAggregatedStats(selection, true);
        }
        return aggregatedDayStats;
    }

    public void updateSelection(TrackSelection selection) {
        loadAggregatedStats(selection);
    }

    public void clearSelection() {
        loadAggregatedStats(new TrackSelection());
    }

    private void loadAggregatedStats(TrackSelection selection) {
        new Thread(() -> {
            ContentProviderUtils contentProviderUtils = new ContentProviderUtils(getApplication().getApplicationContext());
            List<Track> tracks = selection != null ? contentProviderUtils.getTracks(selection) : contentProviderUtils.getTracks();

            // This is where we need to define our custom sorting
            AggregatedStatistics aggregatedStatistics = new AggregatedStatistics(tracks);

            aggregatedStats.postValue(aggregatedStatistics);
        }).start();
    }

    private void loadAggregatedStats(TrackSelection selection, Boolean isDaily) {
        new Thread(() -> {
            ContentProviderUtils contentProviderUtils = new ContentProviderUtils(getApplication().getApplicationContext());
            List<Track> tracks = selection != null ? contentProviderUtils.getTracks(selection) : contentProviderUtils.getTracks();

            // This is where we need to define our custom sorting
            AggregatedStatistics aggregatedStatistics = new AggregatedStatistics(tracks, isDaily);
            if(isDaily) {
                aggregatedDayStats.postValue(aggregatedStatistics);
            } else {
                aggregatedStats.postValue(aggregatedStatistics);
            }
        }).start();
    }
}
