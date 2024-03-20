package de.dennisguse.opentracks.services;

import de.dennisguse.opentracks.data.models;

/**
 * The TrackDifferentiate class is responsible for differentiating ski lift and ski run segments from a track.
 * It analyzes the altitude changes of track points to identify upward movements as ski lifts and downward movements as ski runs.
 * It also utilizes the enum status of TrackPoint to determine if in idle status.
 * The class maintains counts of ski lift and ski run segments, it keeps track of points of lift and run activity and provides method for
 * differentiation of points into the two categories.
 */
public class TrackDifferentiate {

    private final ContentProviderUtils contentProviderUtils;
    private final Track.Id trackId;
    private int liftCount, runCount;
    private TrackPoint.Type prevType;
    private boolean down;

    public TrackDifferentiate() {
        // list of trackpoints grouped per run (index 0 is first run, 1 second run,
        // etc...)
        ArrayList<SkiLiftPoint<TrackPoint>> liftPoints = new ArrayList<SkiLiftPoint<TrackPoint>>();
        ArrayList<SkiLiftPoint<TrackPoint>> runPoints = new ArrayList<SkiRunPoint<TrackPoint>>();

        liftCount = 0;
        runCount = 0;
        prevType = Type.IDLE;

    }

    private void differentiate(Track.Id currentTrack) {
        // iterate through all trackpoints and store them in arraylists
        try (TrackPointIterator tpi = contentProviderUtils.getTrackPointLocationIterator(currentTrack, null)) {
            ArrayList<TrackPoint> track = null;
            while (tpi.hasNext()) {
                TrackPoint trackpoint = tpi.next();

                // if idle point do nothing
                if (trackpoint.getType() == Type.IDLE) {
                    prevType = trackpoint.getType();

                    // if trackpoint starts going up after being idle
                } else if (prevType == Type.IDLE
                        && (trackpoint.getaltitude() < (TrackPoint) tpi.next().getAltitude())) {
                    track = new ArrayList<TrackPoint>();
                    track.add(trackpoint);
                    down = false;

                    // if trackpoint is not idle but next trackpoint does not move in altitude
                } else if (trackpoint.getType() != Type.IDLE
                        && trackpoint.getaltitude() == (TrackPoint) tpi.next().getAltitude()) {
                    track.add(trackpoint);
                    if (down == true) {
                        // if it was marked down add to run list
                        runPoints.set(runCount, track);
                        runCount++;
                    } else if (down == false) {
                        // if marked up add to lift list
                        liftPoints.add(liftCount, track);
                        liftCount++;
                    }

                    // if trackpoint starts going down after being idle
                } else if (prevType == Type.IDLE
                        && (trackpoint.getaltitude() > (TrackPoint) tpi.next().getAltitude())) {
                    track = new ArrayList<TrackPoint>();
                    track.add(trackpoint);
                    down = true;

                }
            }
        }
    }
}
