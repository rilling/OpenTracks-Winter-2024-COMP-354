package de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList; // import the ArrayList class
import java.util.List; // import the List class

/**
 * A module to aid in building the graph for visualizing daily statistics.
 */
public class DailyPlottingModule {

    /**
     * Method that helps calculating moving average.
     *
     * @param entries   This is a List of Entry objects that contains the original daily stats.
     * @param frequency Integer representing the frequency at which moving average is sampled.
     * @return A list containing Entry objects representing the moving averages.
     */
    protected List<Entry> getMovingAverage(List<Entry> entries, int frequency) {
        ArrayList<Entry> toReturn = new ArrayList<Entry>();
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
