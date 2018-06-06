package com.yarmiychuk.udamusicplayer;

import java.util.ArrayList;

/**
 * Created by DmitryYarmiychuk on 30.05.2018.
 * Создал DmitryYarmiychuk 30.05.2018
 */

public class Artist {

    private ArrayList<Track> tracks;
    private String name;

    Artist(String artistName, ArrayList<Track> tracks) {
        this.name = artistName;
        this.tracks = tracks;
    }

    public String getName() {
        return name;
    }

    int getTracksCount() {
        return tracks.size();
    }

    void addTrack(Track track) {
        tracks.add(track);
    }
}
