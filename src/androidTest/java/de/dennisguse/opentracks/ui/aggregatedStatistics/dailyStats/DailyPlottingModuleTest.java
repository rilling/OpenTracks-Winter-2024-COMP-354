package de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats;

import org.junit.Test;

import java.util.ArrayList; // import the ArrayList class
import java.util.List; // import the List class

import com.github.mikephil.charting.data.Entry;

public class DailyPlottingModuleTest {
    @Test
    public void movingAverageCorrect() throws Exception {
        DailyPlottingModule plotModule = new DailyPlottingModule();
        List<Entry> test_entries = new ArrayList<Entry>();
        for (float i = 1f; i <= 10f; i++) {
            test_entries.add(new Entry(i, i));
        }
        int frequency = 3;
        System.out.println("************************");
        System.out.println("ORIGINAL DATA ENTRIES:");
        System.out.println("************************");
        for (int i = 0; i < test_entries.size(); i++)
            System.out.println(test_entries.get(i).toString());

        System.out.println("\n************************");
        System.out.println("MOVING AVERAGE DATA ENTRIES FOR FREQUENCY = " + frequency + " :");
        System.out.println("************************");
        List<Entry> results = plotModule.getMovingAverage(test_entries, frequency);
        for (int i = 0; i < results.size(); i++)
            System.out.println(results.get(i).toString());


    }
}
