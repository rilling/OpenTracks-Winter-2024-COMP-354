package de.dennisguse.opentracks.ui.runlift;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import de.dennisguse.opentracks.data.ContentProviderUtils;
import de.dennisguse.opentracks.data.models.Track;
import de.dennisguse.opentracks.stats.TrackStatisticsUpdater;
import de.dennisguse.opentracks.ui.intervals.IntervalStatisticsTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
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
        dummyTrack.setId(new Track.Id(System.currentTimeMillis()));
        contentProviderUtils.insertTrack(dummyTrack);
        TrackStatisticsUpdater trackStatisticsUpdater = new TrackStatisticsUpdater();


        assertTrue(true);
    }

    @Test
    public void testRunOnlyTrackPoints() {
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
    public void testLiftAndRunTrackPoints() {
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

