package de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList; // import the ArrayList class
import java.util.List; // import the List class

/**
 * A module to aid in building the graph for visualizing daily statistics.
 */
public class DailyPlottingModule {

    public void plotGraph(LineChart lineChart, Metric metric, Frequency frequency) {
        List<Entry> dataEntries;
        LineDataSet metricDataSet;
        LineDataSet runningAverageDataSet;

        switch (metric) {
            case AVG_SLOPE -> {
                dataEntries = getAvgSlopeEntries(DailyRunsProvider.getAllDailyRuns());

                metricDataSet = new LineDataSet(dataEntries, "Average slope line data set");
                metricDataSet.setColor(Color.parseColor("#2774AE"));

                runningAverageDataSet = new LineDataSet(
                        getMovingAverage(dataEntries, frequency.getValue()),
                        "Running average line data set for average slope"
                );
            }
            case AVG_SPEED -> {
                dataEntries = getAvgSpeedEntries(DailyRunsProvider.getAllDailyRuns());

                metricDataSet = new LineDataSet(dataEntries, "Average speed line data set");
                metricDataSet.setColor(Color.parseColor("#ED9121"));

                runningAverageDataSet = new LineDataSet(
                        getMovingAverage(dataEntries, frequency.getValue()),
                        "Running average line data set for average speed"
                );
            }
            case TOTAL_DISTANCE -> {
                dataEntries = getTotalDistanceEntries(DailyRunsProvider.getAllDailyRuns());

                metricDataSet = new LineDataSet(dataEntries, "Total distance line data set");
                metricDataSet.setColor(Color.parseColor("#F8DE7E"));

                runningAverageDataSet = new LineDataSet(
                        getMovingAverage(dataEntries, frequency.getValue()),
                        "Running average line data set for total distance"
                );
            }
            case CHAIRLIFT_SPEED -> {
                dataEntries = getAvgChairliftSpeedEntries(DailyRunsProvider.getAllDailyRuns());

                metricDataSet = new LineDataSet(dataEntries, "Average chairlift speed line data set");
                metricDataSet.setColor(Color.parseColor("#8DB600"));

                runningAverageDataSet = new LineDataSet(
                        getMovingAverage(dataEntries, frequency.getValue()),
                        "Running average line data set for average chairlift speed"
                );
            }
            default -> {
                Log.e("UNKNOWN_PLOT_GRAPH_METRIC", "plotGraph() called with an unknown metric parameter: " + metric);
                return;
            }
        }

        metricDataSet.setLineWidth(4);
        runningAverageDataSet.setLineWidth(4);
        runningAverageDataSet.setColor(Color.parseColor("#F2003C"));

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(metricDataSet);
        dataSets.add(runningAverageDataSet);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    /**
     * Method that helps calculating moving average.
     *
     * @param entries   This is a List of Entry objects that contains the original daily stats.
     * @param frequency Integer representing the frequency at which moving average is sampled.
     * @return A list containing Entry objects representing the moving averages.
     */
    protected List<Entry> getMovingAverage(List<Entry> entries, int frequency) {
        ArrayList<Entry> toReturn = new ArrayList<>();
        for (int e = 0; e <= entries.size() - frequency; e++) {
            //System.out.println("start idx = " + e );
            float x_sum = 0;
            float y_sum = 0;
            float x = (entries.get(e).getX() + (entries.get(e + frequency - 1).getX())) / 2;

            for (int i = e; i < e + frequency; i++) {
                //System.out.println("i = " + i);
                y_sum += entries.get(i).getY();
                x_sum += entries.get(i).getX();
            }
            float y_average = y_sum / frequency;
            float x_average = x_sum / frequency;
            //System.out.println("x = " + x_average );
            toReturn.add(new Entry(x_average, y_average));
        }

        return toReturn;
    }

    private List<Entry> getAvgSpeedEntries(List<RunVM> runs)
    {
        ArrayList<Entry> toReturn = new ArrayList<>();

        if(runs == null || runs.isEmpty())
        {
            return toReturn;
        }

        for(int i=0; i<runs.size();i++)
        {
            int runEntryNumber = i + 1;
            toReturn.add(new Entry(runEntryNumber,runs.get(i).getAvgSpeed()));
        }

        return toReturn;
    }

    private List<Entry> getAvgChairliftSpeedEntries(List<RunVM> runs)
    {
        ArrayList<Entry> toReturn = new ArrayList<>();

        if(runs == null || runs.isEmpty())
        {
            return toReturn;
        }

        for(int i=0; i<runs.size();i++)
        {
            int runEntryNumber = i + 1;
            toReturn.add(new Entry(runEntryNumber,runs.get(i).getChairliftSpeed()));
        }

        return toReturn;
    }

    /**
     * Method that calculates average slope of entries.
     *
     * @param entries   This is a List of Entry objects that contains the original daily stats.
     * @return A list containing Entry objects representing the average slope.
     */
    private List<Entry> getAvgSlopeEntries(List<RunVM> entries){
        ArrayList<Entry> toReturn = new ArrayList<>();


        for (int i = 0; i < entries.size(); i++){
            RunVM run = entries.get(i);
            float avgSlope = run.getAvgSlope();
            toReturn.add(new Entry(i+1, avgSlope));


        }
        return toReturn;


    }


    /**
     * Method that calculates total distance of entries.
     *
     * @param entries   This is a List of Entry objects that contains the original daily stats.
     * @return A list containing Entry objects representing the total distance.
     */
    private List<Entry> getTotalDistanceEntries(List<RunVM> entries){
        ArrayList<Entry> toReturn = new ArrayList<>();


        for (int i = 0; i < entries.size(); i++){
            RunVM run = entries.get(i);
            float totalDistance = run.getTotalDistance();
            toReturn.add(new Entry(i+1, totalDistance));


        }
        return toReturn;


    }



}
