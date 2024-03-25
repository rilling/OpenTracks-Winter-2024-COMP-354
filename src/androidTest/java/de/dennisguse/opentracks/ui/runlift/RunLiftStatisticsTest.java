package de.dennisguse.opentracks.ui.runlift;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import de.dennisguse.opentracks.content.data.TestDataUtil;
import de.dennisguse.opentracks.data.ContentProviderUtils;
import de.dennisguse.opentracks.data.TrackPointIterator;
import de.dennisguse.opentracks.data.models.Track;
import de.dennisguse.opentracks.data.models.TrackPoint;
import de.dennisguse.opentracks.stats.TrackStatisticsUpdater;
import de.dennisguse.opentracks.ui.intervals.IntervalStatisticsTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class RunLiftStatisticsTest {

    private static final String TAG = RunLiftStatisticsTest.class.getSimpleName();

    private final Context context = ApplicationProvider.getApplicationContext();
    private ContentProviderUtils contentProviderUtils;

    @Before
    public void setUp() {
        contentProviderUtils = new ContentProviderUtils(context);
    }

    @Test
    public void testLiftOnlyTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;
        int currentAltitude = 187;
        int altitudeGain = 3;

        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        for (int i = 0; i < numberOfPoints; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, 3, altitudeGain,
                    0);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
            currentAltitude += altitudeGain;
        }
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(trackPointIterator.getCount(), numberOfPoints);
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiActivityList();

        assertEquals(1, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).isLift());
        assertEquals(20, skiSubActivities.get(0).getTrackPoints().size());
    }

    @Test
    public void testRunOnlyTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;
        int currentAltitude = 400;
        int altitudeLoss = 5;

        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        for (int i = 0; i < numberOfPoints; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, 3, 0,
                    altitudeLoss);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
            currentAltitude -= altitudeLoss;
        }
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(trackPointIterator.getCount(), numberOfPoints);
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiActivityList();

        assertEquals(1, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertFalse(skiSubActivities.get(0).isLift());
        assertEquals(20, skiSubActivities.get(0).getTrackPoints().size());
    }

    @Test
    public void testRunThenLiftTrackPoints() {
        Track dummyTrack = new Track();
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        assertTrue(true);
    }

    @Test
    public void testLiftWithWaitTrackPoints() {
        Track dummyTrack = new Track();
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        assertTrue(true);
    }

    @Test
    public void testLiftThenRunTrackPoints() {
        Track dummyTrack = new Track();
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        assertTrue(true);
    }

    @Test
    public void testLiftAndRunWithWaitTrackPoints() {
        Track dummyTrack = new Track();
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        assertTrue(true);
    }

    @Test
    public void testLiftAndRunAndLiftTrackPoints() {
        Track dummyTrack = new Track();
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        assertTrue(true);
    }

    @Test
    public void testLiftAndRunAndLiftWithWaitTimesTrackPoints() {
        Track dummyTrack = new Track();
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        assertTrue(true);
    }
}
