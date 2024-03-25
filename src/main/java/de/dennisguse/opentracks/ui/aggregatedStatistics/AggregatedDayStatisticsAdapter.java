package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.data.models.ActivityType;
import de.dennisguse.opentracks.data.models.DistanceFormatter;
import de.dennisguse.opentracks.data.models.SpeedFormatter;
import de.dennisguse.opentracks.databinding.AggregatedDailyStatsListItemBinding;
import de.dennisguse.opentracks.databinding.AggregatedStatsListItemBinding;
import de.dennisguse.opentracks.settings.PreferencesUtils;
import de.dennisguse.opentracks.settings.UnitSystem;
import de.dennisguse.opentracks.util.StringUtils;

public class AggregatedDayStatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AggregatedStatistics aggregatedStatistics;
    private SimpleDateFormat formatter = new SimpleDateFormat("MM dd yyy");

    private final Context context;

    public AggregatedDayStatisticsAdapter(Context context, AggregatedStatistics aggregatedStatistics) {
        this.context = context;
        this.aggregatedStatistics = aggregatedStatistics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AggregatedDailyStatsListItemBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        AggregatedStatistics.AggregatedStatistic aggregatedStatistic = aggregatedStatistics.getItem(position);

        String type = aggregatedStatistic.getActivityTypeLocalized();
        if (ActivityType.findByLocalizedString(context, type).isShowSpeedPreferred()) {
            viewHolder.setSpeed(aggregatedStatistic);
        } else {
            viewHolder.setPace(aggregatedStatistic);
        }
    }

    @Override
    public int getItemCount() {
        if (aggregatedStatistics == null) {
            return 0;
        }
        return aggregatedStatistics.getCount();
    }

    public void swapData(AggregatedStatistics aggregatedStatistics) {
        this.aggregatedStatistics = aggregatedStatistics;
        this.notifyDataSetChanged();
    }

    public List<String> getDays() {
        List<String> days = new ArrayList<>();
        for (int i = 0; i < aggregatedStatistics.getCount(); i++) {
            Date day = Date.from(aggregatedStatistics.getItem(i).getTrackStatistics().getStopTime());
            days.add(formatter.format(day));
        }
        return days;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private final AggregatedDailyStatsListItemBinding viewBinding;
        private UnitSystem unitSystem = UnitSystem.defaultUnitSystem();
        private boolean reportSpeed;

        public ViewHolder(AggregatedDailyStatsListItemBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }

        public void setSpeed(AggregatedStatistics.AggregatedStatistic aggregatedStatistic) {
            setCommonValues(aggregatedStatistic);

            SpeedFormatter formatter = SpeedFormatter.Builder().setUnit(unitSystem).setReportSpeedOrPace(reportSpeed).build(context);
            {
                //TODO Fill in the infomation here
                Pair<String, String> parts = formatter.getSpeedParts(aggregatedStatistic.getTrackStatistics().getAverageMovingSpeed());
//                viewBinding.aggregatedStatsAvgRate.setText(parts.first);
//                viewBinding.aggregatedStatsAvgRateUnit.setText(parts.second);
//                viewBinding.aggregatedStatsAvgRateLabel.setText(context.getString(R.string.stats_average_moving_speed));
                // Average Run Speed
                viewBinding.dailyRunAvgSpeed.setText(parts.first);
                viewBinding.dailyRunAvgSpeedUnit.setText(parts.second);
                viewBinding.dailyRunAvgSpeedLabel.setText(context.getString(R.string.daily_run_avg_speed_label));
            }

            {
                //TODO Fill in the information here
//                Pair<String, String> parts = formatter.getSpeedParts(aggregatedStatistic.getTrackStatistics().getMaxSpeed());
//                viewBinding.aggregatedStatsMaxRate.setText(parts.first);
//                viewBinding.aggregatedStatsMaxRateUnit.setText(parts.second);
//                viewBinding.aggregatedStatsMaxRateLabel.setText(context.getString(R.string.stats_max_speed));
            }
        }

        public void setPace(AggregatedStatistics.AggregatedStatistic aggregatedStatistic) {
            setCommonValues(aggregatedStatistic);

            SpeedFormatter formatter = SpeedFormatter.Builder().setUnit(unitSystem).setReportSpeedOrPace(reportSpeed).build(context);
            {
                //TODO Fill in the information here
//                Pair<String, String> parts = formatter.getSpeedParts(aggregatedStatistic.getTrackStatistics().getAverageMovingSpeed());
//                viewBinding.aggregatedStatsAvgRate.setText(parts.first);
//                viewBinding.aggregatedStatsAvgRateUnit.setText(parts.second);
//                viewBinding.aggregatedStatsAvgRateLabel.setText(context.getString(R.string.stats_average_moving_pace));
            }

            {
                //TODO Fill in the information here
//                Pair<String, String> parts = formatter.getSpeedParts(aggregatedStatistic.getTrackStatistics().getMaxSpeed());
//                viewBinding.aggregatedStatsMaxRate.setText(parts.first);
//                viewBinding.aggregatedStatsMaxRateUnit.setText(parts.second);
//                viewBinding.aggregatedStatsMaxRateLabel.setText(R.string.stats_fastest_pace);
            }
        }

        //TODO Check preference handling.
        private void setCommonValues(AggregatedStatistics.AggregatedStatistic aggregatedStatistic) {
            String activityType = aggregatedStatistic.getActivityTypeLocalized();
            String day = aggregatedStatistic.getDay();

            reportSpeed = PreferencesUtils.isReportSpeed(activityType);
            unitSystem = PreferencesUtils.getUnitSystem();

            viewBinding.activityIcon.setImageResource(getIcon(aggregatedStatistic));
            viewBinding.aggregatedStatsDayLabel.setText(day);
            viewBinding.aggregatedStatsNumDayTracks.setText(StringUtils.valueInParentheses(String.valueOf(aggregatedStatistic.getCountTracks())));

            Pair<String, String> parts = DistanceFormatter.Builder()
                    .setUnit(unitSystem)
                    .build(context).getDistanceParts(aggregatedStatistic.getTrackStatistics().getTotalDistance());
            viewBinding.dailyTotalDistanceNumber.setText(parts.first);
            viewBinding.dailyTotalDistanceUnit.setText(context.getString(R.string.daily_total_distance_unit));
            viewBinding.dailyTotalDistanceLabel.setText(context.getString(R.string.daily_total_distance_label));

//           viewBinding.aggregatedStatsTime.setText(StringUtils.formatElapsedTime(aggregatedStatistic.getTrackStatistics().getMovingTime()));
            // Number of Lifts
            viewBinding.dailyLiftNumber.setText(String.valueOf(aggregatedStatistic.getCountTracks()));
            viewBinding.dailyLiftNumberUnit.setText(context.getString(R.string.daily_lift_number_unit));
            viewBinding.dailyLiftNumberLabel.setText(context.getString(R.string.daily_lift_number_label));
            // Lift Total Time
            viewBinding.dailyLiftTotalTime.setText(String.valueOf(aggregatedStatistic.getTotalTime()));
            viewBinding.dailyLiftTotalTimeLabel.setText(context.getString(R.string.daily_lift_total_time_label));
            // Lift Moving Time
            viewBinding.dailyLiftMovingTime.setText(String.valueOf(aggregatedStatistic.getMovingTime()));
            viewBinding.dailyLiftMovingTimeLabel.setText(context.getString(R.string.daily_lift_moving_time_label));

            // Number of Runs
            viewBinding.dailyRunNumber.setText(String.valueOf(aggregatedStatistic.getCountTracks()));
            viewBinding.dailyRunNumberUnit.setText(context.getString(R.string.daily_run_number_unit));
            viewBinding.dailyRunNumberLabel.setText(context.getString(R.string.daily_run_number_label));

            // Run Elevation
            viewBinding.dailyRunMaxVertical.setText(String.valueOf(aggregatedStatistic.getMaxVertical()));
            viewBinding.dailyRunMaxVerticalUnit.setText(context.getString(R.string.daily_run_max_vertical_unit));
            viewBinding.dailyRunMaxVerticalLabel.setText(context.getString(R.string.daily_run_max_vertical_label));

            //Activity type
            viewBinding.activityTypeLabel.setText(String.valueOf(aggregatedStatistic.getActivityTypeLocalized()));
        }

        private int getIcon(AggregatedStatistics.AggregatedStatistic aggregatedStatistic) {
            String localizedActivityType = aggregatedStatistic.getActivityTypeLocalized();
            return ActivityType.findByLocalizedString(context, localizedActivityType)
                    .getIconDrawableId();
        }
    }
}
