package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.stats.MockupData;
import de.dennisguse.opentracks.stats.TrackStatistics;
public class StatisticsPreviewAdapter extends RecyclerView.Adapter<StatisticsPreviewAdapter.ViewHolder> {
    private List<MockupData> list = new ArrayList<>();

    public StatisticsPreviewAdapter(List<MockupData> mList){
        this.list = mList;
    }

    public int getItemCount(){
        return list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position){

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stats_preview_item, viewGroup, false);

        v.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StatisticsActivity.class);
                intent.putExtra("data", list.get(position));
                v.getContext().startActivity(intent);
            }
        });

        return new ViewHolder(v);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int i){
        MockupData data = list.get(i);

        int year = data.getSeason();
        List<TrackStatistics> trackStatistics = data.getTrackStatistics();
        TrackStatistics summary = TrackStatistics.sumOfTotalStats(trackStatistics);

        holder.previewInfo.setText(String.valueOf(year) + " - " + String.valueOf(year+1)); // TODO: do this the right way : |

        String days = String.valueOf(summary.getTotalSkiDaysSeason());
        String distance = String.valueOf(summary.getTotalDistance().toM());
        String runs = String.valueOf(summary.getTotalRunsSeason());

        holder.season.setText(days + " days | " + distance + "m | " + runs + " runs");
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView season;
        TextView previewInfo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            season = itemView.findViewById(R.id.seasonPreview);
            previewInfo = itemView.findViewById(R.id.seasonPreviewData);
        }
    }
}
