package de.dennisguse.opentracks.ui.runlift;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import de.dennisguse.opentracks.content.data.TestDataUtil;
import de.dennisguse.opentracks.data.ContentProviderUtils;
import de.dennisguse.opentracks.data.TrackPointIterator;
import de.dennisguse.opentracks.data.models.Track;
import de.dennisguse.opentracks.data.models.TrackPoint;
import de.dennisguse.opentracks.stats.TrackStatisticsUpdater;

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

        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createLift(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(1, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).getSlopePercentage() > 0);
        assertTrue(skiSubActivities.get(0).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
    }

    @Test
    public void testRunOnlyTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createRun(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(1, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).getSlopePercentage() > 0);
        assertFalse(skiSubActivities.get(0).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
    }

    @Test
    public void testLiftThenRunTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createLift(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0);
        createRun(dummyTrack, trackStatisticsUpdater, numberOfPoints, numberOfPoints);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints * 2, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(2, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertEquals(0.0, skiSubActivities.get(1).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).isLift());
        assertFalse(skiSubActivities.get(1).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
        assertEquals(numberOfPoints, skiSubActivities.get(1).getTrackPoints().size());
    }

    @Test
    public void testRunThenLiftTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createRun(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0);
        createLift(dummyTrack, trackStatisticsUpdater, numberOfPoints, numberOfPoints);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints * 2, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(2, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertEquals(0.0, skiSubActivities.get(1).getWaitTime().toSeconds(), 0.01);
        assertFalse(skiSubActivities.get(0).isLift());
        assertTrue(skiSubActivities.get(1).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
        assertEquals(numberOfPoints, skiSubActivities.get(1).getTrackPoints().size());
    }

    @Test
    public void testLiftWithWaitTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;

        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createLiftWithWait(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(1, skiSubActivities.size());
        assertEquals(4.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).getSlopePercentage() > 0);
        assertTrue(skiSubActivities.get(0).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
    }

    @Test
    public void testLiftAndRunWithWaitTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;

        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createLiftWithWait(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0);
        createRun(dummyTrack, trackStatisticsUpdater, numberOfPoints, numberOfPoints);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints * 2, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(2, skiSubActivities.size());
        assertEquals(4.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).getSlopePercentage() > 0);
        assertTrue(skiSubActivities.get(0).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
    }

    @Test
    public void testLiftAndRunAndLiftTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createLift(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0);
        createRun(dummyTrack, trackStatisticsUpdater, numberOfPoints, numberOfPoints);
        createLift(dummyTrack, trackStatisticsUpdater, numberOfPoints, numberOfPoints * 2);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints * 3, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(3, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertEquals(0.0, skiSubActivities.get(1).getWaitTime().toSeconds(), 0.01);
        assertEquals(0.0, skiSubActivities.get(2).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).isLift());
        assertFalse(skiSubActivities.get(1).isLift());
        assertTrue(skiSubActivities.get(2).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
        assertEquals(numberOfPoints, skiSubActivities.get(1).getTrackPoints().size());
        assertEquals(numberOfPoints, skiSubActivities.get(2).getTrackPoints().size());
    }

    @Test
    public void testRunAndLiftAndRunTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createRun(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0);
        createLift(dummyTrack, trackStatisticsUpdater, numberOfPoints, numberOfPoints);
        createRun(dummyTrack, trackStatisticsUpdater, numberOfPoints, numberOfPoints * 2);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints * 3, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(3, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertEquals(0.0, skiSubActivities.get(1).getWaitTime().toSeconds(), 0.01);
        assertEquals(0.0, skiSubActivities.get(2).getWaitTime().toSeconds(), 0.01);
        assertFalse(skiSubActivities.get(0).isLift());
        assertTrue(skiSubActivities.get(1).isLift());
        assertFalse(skiSubActivities.get(2).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
        assertEquals(numberOfPoints, skiSubActivities.get(1).getTrackPoints().size());
        assertEquals(numberOfPoints, skiSubActivities.get(2).getTrackPoints().size());
    }

    @Test
    public void testLiftAndRunAndLiftWithWaitTimesTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createLiftWithWait(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0);
        createRun(dummyTrack, trackStatisticsUpdater, numberOfPoints, numberOfPoints);
        createLiftWithWait(dummyTrack, trackStatisticsUpdater, numberOfPoints, numberOfPoints * 2);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints * 3, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(3, skiSubActivities.size());
        assertEquals(4.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertEquals(0.0, skiSubActivities.get(1).getWaitTime().toSeconds(), 0.01);
        assertEquals(5.0, skiSubActivities.get(2).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).isLift());
        assertFalse(skiSubActivities.get(1).isLift());
        assertTrue(skiSubActivities.get(2).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
        assertEquals(numberOfPoints, skiSubActivities.get(1).getTrackPoints().size());
        assertEquals(numberOfPoints, skiSubActivities.get(2).getTrackPoints().size());
    }

    @Test
    public void testWaitInLiftTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;

        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createLiftWithInnerWaits(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0, 3);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(1, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).getSlopePercentage() > 0);
        assertTrue(skiSubActivities.get(0).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
    }

    @Test
    public void testWaitInRunTrackPoints() {
        Track dummyTrack = new Track();
        int numberOfPoints = 20;
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();

        createRunWithInnerWaits(dummyTrack, trackStatisticsUpdater, numberOfPoints, 0, 3);
        dummyTrack.setTrackStatistics(trackStatisticsUpdater.getTrackStatistics());
        contentProviderUtils.updateTrack(dummyTrack);

        RunLiftStatistics runLiftStatistics = new RunLiftStatistics();

        try (TrackPointIterator trackPointIterator = contentProviderUtils.getTrackPointLocationIterator(dummyTrack.getId(), null)) {
            assertEquals(numberOfPoints, trackPointIterator.getCount());
            runLiftStatistics.addTrackPoints(trackPointIterator);
        }

        List<RunLiftStatistics.SkiSubActivity> skiSubActivities = runLiftStatistics.getSkiSubActivityList();

        assertEquals(1, skiSubActivities.size());
        assertEquals(0.0, skiSubActivities.get(0).getWaitTime().toSeconds(), 0.01);
        assertTrue(skiSubActivities.get(0).getSlopePercentage() > 0);
        assertFalse(skiSubActivities.get(0).isLift());
        assertEquals(numberOfPoints, skiSubActivities.get(0).getTrackPoints().size());
    }

    private void createLift(Track dummyTrack, TrackStatisticsUpdater trackStatisticsUpdater, int numberOfPoints, int offset) {
        int currentAltitude = 187;
        int altitudeGain = 3;
        for (int i = offset; i < offset + numberOfPoints; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, 3, altitudeGain,
                    0);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
            currentAltitude += altitudeGain;
        }
    }

    private void createLiftWithWait(Track dummyTrack, TrackStatisticsUpdater trackStatisticsUpdater, int numberOfPoints, int offset) {
        int currentAltitude = 187;
        int waitPointCount = 4;
        int altitudeGain = 3;
        int i = offset;
        // add some wait times
        for (; i < offset + waitPointCount; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, (i-offset) % 2,
                    Math.abs((i-offset) % 2 - 1),
                    (i-offset) % 2);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
        }
        for (; i < offset + numberOfPoints; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, 3, altitudeGain,
                    0);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
            currentAltitude += altitudeGain;
        }
    }

    private void createRun(Track dummyTrack, TrackStatisticsUpdater trackStatisticsUpdater, int numberOfPoints, int offset) {
        int currentAltitude = 400;
        int altitudeLoss = 5;
        for (int i = offset; i < offset + numberOfPoints; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, 4, 0,
                    altitudeLoss);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
            currentAltitude -= altitudeLoss;
        }
    }

    private void createLiftWithInnerWaits(Track dummyTrack, TrackStatisticsUpdater trackStatisticsUpdater, int numberOfPoints, int offset, int waitPoints) {
        int currentAltitude = 187;
        int waitPointCount = 4;
        int altitudeGain = 3;
        int i = offset;
        for (; i < (offset + numberOfPoints) / 2; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, 3, altitudeGain,
                    0);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
            currentAltitude += altitudeGain;
        }
        for (; i < (offset + numberOfPoints) / 2 + waitPoints; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, (i - offset) % 2,
                    Math.abs((i - offset) % 2 - 1),
                    (i - offset) % 2);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
        }
        for (; i < offset + numberOfPoints; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, 3, altitudeGain,
                    0);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
            currentAltitude += altitudeGain;
        }
    }

    private void createRunWithInnerWaits(Track dummyTrack, TrackStatisticsUpdater trackStatisticsUpdater, int numberOfPoints, int offset, int waitPoints) {
        int currentAltitude = 400;
        int altitudeLoss = 5;
        int i = offset;
        for (; i < (offset + numberOfPoints) / 2; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, 4, 0,
                    altitudeLoss);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
            currentAltitude -= altitudeLoss;
        }
        for (; i < (offset + numberOfPoints) / 2 + waitPoints; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, (i - offset) % 2,
                    Math.abs((i - offset) % 2 - 1),
                    (i - offset) % 2);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
        }
        for (; i < offset + numberOfPoints; i++) {
            TrackPoint tp = TestDataUtil.createTrackPoint(i, currentAltitude, 4, 0,
                    altitudeLoss);
            contentProviderUtils.insertTrackPoint(tp, dummyTrack.getId());
            trackStatisticsUpdater.addTrackPoint(tp);
            currentAltitude -= altitudeLoss;
        }
    }
}
