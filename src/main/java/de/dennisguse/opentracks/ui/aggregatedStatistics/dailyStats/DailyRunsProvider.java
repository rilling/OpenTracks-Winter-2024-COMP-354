package de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats;

import java.util.*;

/**
 * A mock service to provide dummy data to test the visualize daily statistics feature.
 * ALL PARAMETERS ARE ASSUMED TO BE IN METERS OR METERS/SECOND
 * Realistic beginner skier speed 0-15mph (0-6.7m/s)
 * Realistic intermediate skier speed 20-30mph (8.9-13.4m/s)
 * Chairlift's speed depends on detachable or fixed-grip, moving at 5.08m/s or 2.5m/s, respectively
 * Some real-life ski slopes with elevation difference/length that were used to calculate realistic average speeds, slopes, distances:
 *  1) Slopes Kicking Horse: Longest hill - (1160m elevation, 10000m length), Hardest hill - (400m elevation, 1000m length)
 *  2) Slopes Tremblant: Longest hill - (645m elevation, 6000m length), Hardest hill - (330m elevation, 1000m)
 *  3) Slopes Mont-Sainte-Anne: Longest hill - (625m elevation, 5700m length), Hardest hill - (363m elevation, 900m)
 */
public class DailyRunsProvider {
    private static int runIdCtr = 0;
    private static List<RunVM> getDailyRunsSample1()
    {
        ArrayList<RunVM> runs = new ArrayList<RunVM>();

        RunVM dummyRun1 = new RunVM(runIdCtr, 4.2f, 0.116f, 5.08f, 10000);
        runIdCtr++;
        RunVM dummyRun2 = new RunVM(runIdCtr, 12.3f, 0.4f, 5.08f, 1000);
        runIdCtr++;

        runs.add(dummyRun1);
        runs.add(dummyRun2);
        return runs;
    }
    private static List<RunVM> getDailyRunsSample2()
    {
        ArrayList<RunVM> runs = new ArrayList<RunVM>();

        RunVM dummyRun1 = new RunVM(runIdCtr, 4.5f, 0.170f, 2.5f, 3000);
        runIdCtr++;
        RunVM dummyRun2 = new RunVM(runIdCtr, 14.1f, 0.44f, 5.08f, 900);
        runIdCtr++;
        RunVM dummyRun3 = new RunVM(runIdCtr, 11.7f, 0.37f, 4.5f, 1000);
        runIdCtr++;
        RunVM dummyRun4 = new RunVM(runIdCtr, 2.9f, 0.1075f, 2.58f, 6000);
        runIdCtr++;
        RunVM dummyRun5 = new RunVM(runIdCtr, 12.9f, 0.403f, 4.7f, 950);
        runIdCtr++;

        runs.add(dummyRun1);
        runs.add(dummyRun2);
        runs.add(dummyRun3);
        runs.add(dummyRun4);
        runs.add(dummyRun5);
        return runs;
    }
    private static List<RunVM> getDailyRunsSample3()
    {
        ArrayList<RunVM> runs = new ArrayList<RunVM>();

        RunVM dummyRun1 = new RunVM(runIdCtr, 2.65f, 0.09f, 2.1f, 6000);
        runIdCtr++;
        RunVM dummyRun2 = new RunVM(runIdCtr, 3.15f, 0.105f, 2.3f, 3550);
        runIdCtr++;
        RunVM dummyRun3 = new RunVM(runIdCtr, 15.9f, 0.472f, 5.5f, 1750);
        runIdCtr++;
        RunVM dummyRun4 = new RunVM(runIdCtr, 11.1f, 0.333f, 2.58f, 6000);
        runIdCtr++;
        RunVM dummyRun5 = new RunVM(runIdCtr, 18.9f, 0.49f, 5.5f, 750);
        runIdCtr++;
        RunVM dummyRun6 = new RunVM(runIdCtr, 8.175f, 0.275f, 4.5f, 3000);
        runIdCtr++;
        RunVM dummyRun7 = new RunVM(runIdCtr, 3.05f, 0.1f, 2f, 9000);
        runIdCtr++;
        RunVM dummyRun8 = new RunVM(runIdCtr, 5.33f, 0.225f, 3.5f, 6000);
        runIdCtr++;

        runs.add(dummyRun1);
        runs.add(dummyRun2);
        runs.add(dummyRun3);
        runs.add(dummyRun4);
        runs.add(dummyRun5);
        runs.add(dummyRun6);
        runs.add(dummyRun7);
        runs.add(dummyRun8);
        return runs;
    }
    private static List<RunVM> getDailyRunsSample4()
    {
        ArrayList<RunVM> runs = new ArrayList<RunVM>();

        RunVM dummyRun1 = new RunVM(runIdCtr, 5.5f, 0.21f, 3f, 7000);
        runIdCtr++;
        RunVM dummyRun2 = new RunVM(runIdCtr, 6.33f, 0.22f, 3.5f, 6500);
        runIdCtr++;
        RunVM dummyRun3 = new RunVM(runIdCtr, 7.75f, 0.24f, 3.9f, 5000);
        runIdCtr++;
        RunVM dummyRun4 = new RunVM(runIdCtr, 5.3f, 0.233f, 4.0f, 6550);
        runIdCtr++;
        RunVM dummyRun5 = new RunVM(runIdCtr, 13.4f, 0.39f, 4.7f, 1100);
        runIdCtr++;
        RunVM dummyRun6 = new RunVM(runIdCtr, 12.4f, 0.38f, 4.85f, 1100);
        runIdCtr++;
        RunVM dummyRun7 = new RunVM(runIdCtr, 7.1f, 0.2575f, 3.5f, 6000);
        runIdCtr++;
        RunVM dummyRun8 = new RunVM(runIdCtr, 6.667f, 0.225f, 5.8f, 9000);
        runIdCtr++;
        RunVM dummyRun9 = new RunVM(runIdCtr, 6.9f, 0.23f, 5.66f, 9000);
        runIdCtr++;
        RunVM dummyRun10 = new RunVM(runIdCtr, 20.5f, 0.5125f, 5.8f, 750);
        runIdCtr++;

        runs.add(dummyRun1);
        runs.add(dummyRun2);
        runs.add(dummyRun3);
        runs.add(dummyRun4);
        runs.add(dummyRun5);
        runs.add(dummyRun6);
        runs.add(dummyRun7);
        runs.add(dummyRun8);
        runs.add(dummyRun9);
        runs.add(dummyRun10);
        return runs;
    }
    private static List<RunVM> getDailyRunsSample5()
    {
        ArrayList<RunVM> runs = new ArrayList<RunVM>();

        RunVM dummyRun1 = new RunVM(runIdCtr, 12.5f, 0.350f, 6.5f, 900);
        runIdCtr++;
        RunVM dummyRun2 = new RunVM(runIdCtr, 14.1f, 0.44f, 5.75f, 1100);
        runIdCtr++;
        RunVM dummyRun3 = new RunVM(runIdCtr, 11.7f, 0.33f, 5.5f, 1200);
        runIdCtr++;
        RunVM dummyRun4 = new RunVM(runIdCtr, 13.8f, 0.385f, 5.25f, 1000);
        runIdCtr++;
        RunVM dummyRun5 = new RunVM(runIdCtr, 13.9f, 0.403f, 5.15f, 950);
        runIdCtr++;
        RunVM dummyRun6 = new RunVM(runIdCtr, 12.1f, 0.38f, 4.85f, 975);
        runIdCtr++;
        RunVM dummyRun7 = new RunVM(runIdCtr, 7.1f, 0.21f, 3.5f, 6000);
        runIdCtr++;
        RunVM dummyRun8 = new RunVM(runIdCtr, 6.667f, 0.18f, 3.8f, 4500);
        runIdCtr++;
        RunVM dummyRun9 = new RunVM(runIdCtr, 6.9f, 0.23f, 5.2f, 5000);
        runIdCtr++;
        RunVM dummyRun10 = new RunVM(runIdCtr, 3.3f, 0.133f, 5.4f, 7500);
        runIdCtr++;
        RunVM dummyRun11 = new RunVM(runIdCtr, 3f, 0.105f, 5.8f, 12000);
        runIdCtr++;
        RunVM dummyRun12 = new RunVM(runIdCtr, 3.55f, 0.155f, 2.9f, 8000);
        runIdCtr++;
        RunVM dummyRun13 = new RunVM(runIdCtr, 13.4f, 0.375f, 4.2f, 1050);
        runIdCtr++;
        RunVM dummyRun14 = new RunVM(runIdCtr, 14.1f, 0.388f, 3.9f, 1025);
        runIdCtr++;

        runs.add(dummyRun1);
        runs.add(dummyRun2);
        runs.add(dummyRun3);
        runs.add(dummyRun4);
        runs.add(dummyRun5);
        runs.add(dummyRun6);
        runs.add(dummyRun7);
        runs.add(dummyRun8);
        runs.add(dummyRun9);
        runs.add(dummyRun10);
        runs.add(dummyRun11);
        runs.add(dummyRun12);
        runs.add(dummyRun13);
        runs.add(dummyRun14);
        return runs;
    }

    public static List<RunVM> getAllDailyRuns()
    {
        int randomNum = (int) (Math.random()*10)+1;

        if(randomNum == 1 || randomNum == 2)
        {
            return getDailyRunsSample1();
        }
        else if(randomNum == 3 || randomNum == 4)
        {
            return getDailyRunsSample2();
        }
        else if(randomNum == 5 || randomNum == 6)
        {
            return getDailyRunsSample3();
        }
        else if(randomNum == 7 || randomNum == 8)
        {
            return getDailyRunsSample4();
        }
        else
        {
            return getDailyRunsSample5();
        }
    }
}
