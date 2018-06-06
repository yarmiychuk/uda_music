package com.yarmiychuk.udamusicplayer;

/**
 * Created by Dmitry Yarmiychuk on 07.03.2018.
 * Создал Dmitry Yarmiychuk 07.03.2018
 */

public class Track {

    private String title, artist, album;

    Track(String title, String artist, String album) {
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }
}
