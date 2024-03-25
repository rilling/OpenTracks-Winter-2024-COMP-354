package de.dennisguse.opentracks.services;

import android.content.Context;

import java.util.ArrayList;

import de.dennisguse.opentracks.data.ContentProviderUtils;
import de.dennisguse.opentracks.data.TrackPointIterator;
import de.dennisguse.opentracks.data.models.*;

/**
 * The TrackDifferentiate class is responsible for differentiating ski lift and ski run segments from a track.
 * It analyzes the altitude changes of track points to identify upward movements as ski lifts and downward movements as ski runs.
 * It also utilizes the enum status of TrackPoint to determine if in idle status.
 * The class maintains counts of ski lift and ski run segments, it keeps track of points of lift and run activity and provides method for
 * differentiation of points into the two categories.
 */
public class TrackDifferentiate {
    //private final ContentProviderUtils contentProviderUtils = new ContentProviderUtils();
    private Track.Id trackId;
    private int liftCount, runCount;
    private TrackPoint.Type prevType;
    private boolean down;
    private ArrayList<SkiLiftPoint> liftPoints;
    private ArrayList<SkiRunPoint> runPoints;

    public TrackDifferentiate() {
        // list of trackpoints grouped per run (index 0 is first run, 1 second run,
        // etc...)
        liftPoints = new ArrayList<>();
        runPoints = new ArrayList<>();

        liftCount = 0;
        runCount = 0;
        prevType = TrackPoint.Type.IDLE;

    }

    private void differentiate(Track.Id currentTrack, Context context) {
        ContentProviderUtils contentProviderUtils = new ContentProviderUtils(context);

        // iterate through all trackpoints and store them in arraylists
        try (TrackPointIterator tpi = contentProviderUtils.getTrackPointLocationIterator(currentTrack, null)) {
            ArrayList<TrackPoint> track = null;

            while (tpi.hasNext()) {
                TrackPoint trackpoint = tpi.next();

                // if idle point do nothing
                if (trackpoint.getType() == TrackPoint.Type.IDLE) {
                    prevType = trackpoint.getType();
                }
                // if trackpoint starts going up after being idle
                else if (prevType == TrackPoint.Type.IDLE
                        && (trackpoint.getAltitude().toM() < tpi.next().getAltitude().toM())) {
                    track = new ArrayList<TrackPoint>();
                    track.add(trackpoint);
                    down = false;
                }
                // if trackpoint is not idle but next trackpoint does not move in altitude
                else if (trackpoint.getType() != TrackPoint.Type.IDLE
                        && trackpoint.getAltitude().toM() == tpi.next().getAltitude().toM()) {
                    track.add(trackpoint);

                    if (down) {
                        // if it was marked down add to run list
                        runPoints.set(runCount, (SkiRunPoint) track.get(runCount));
                        runCount++;
                    }
                    else if (!down) {
                        // if marked up add to lift list
                        liftPoints.add(liftCount, (SkiLiftPoint) track.get(liftCount));
                        liftCount++;
                    }
                }
                // if trackpoint starts going down after being idle
                else if (prevType == TrackPoint.Type.IDLE
                        && (trackpoint.getAltitude().toM() > tpi.next().getAltitude().toM())) {
                    track = new ArrayList<TrackPoint>();
                    track.add(trackpoint);
                    down = true;

                }
            }
        }
    }
}
