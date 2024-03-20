package de.dennisguse.opentracks.services;

import de.dennisguse.opentracks.data.models;

public class TrackDifferentiate {

    private final ContentProviderUtils contentProviderUtils;
    private final Track.Id trackId;
    private int liftCount, runCount;

    public TrackDifferentiate() {
        // list of tracks that contains all the trackpoints of each run
        ArrayList<ArrayList<SkiLiftPoint>> lifts = new ArrayList<ArrayList<SkiLiftPoint>>();
        ArrayList<ArrayList<SkiRunPoint>> runs = new ArrayList<ArrayList<SkiRunPoint>>();

        // list of trackpoints grouped per run
        ArrayList<SkiLiftPoint> liftPoints = new ArrayList<SkiLiftPoint>();
        ArrayList<SkiLiftPoint> runPoints = new ArrayList<SkiRunPoint>();

        liftCount = 0;
        runCount = 0;
    }

    private void differentiate(Track.Id currentTrack){
        try (TrackPointIterator tpi = contentProviderUtils.getTrackPointLocationIterator(currentTrack, null)) {
            while(tpi.hasNext()){
                TrackPoint trackpoint = tpi.next();
                TrackPoint.Type
                if(trackpoint.getType() == Type.IDLE){
                    //do nothing
                }else if(trackpoint.getType() == Type.IDLE){

                }
            }
        }
    }
}
