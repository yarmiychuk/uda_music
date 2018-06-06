package com.yarmiychuk.udamusicplayer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by DmitryYarmiychuk on 15.04.2018.
 * Создал DmitryYarmiychuk 15.04.2018
 */

public class MediaPicker {

    private static final int NOT_FOUND = -1;
    private static final int MINIMUM_DURATION = 1000 * 60; // 60 seconds

    /**
     * Get media files from device and return list of that track
     *
     * @param context    - Activity as context
     * @param artistName - Name of artist for selection,
     *                   can be null if you want to get all the files
     * @return list of tracks was find
     */
    public static ArrayList<Track> getTracks(Context context, String artistName) {

        ArrayList<Track> listOfTracks = new ArrayList<>();

        Uri storageUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = makeCursor(context, storageUri);

        if (cursor != null) {
            if (!cursor.moveToFirst()) {

                // No tracks
                Toast.makeText(context, context.getString(R.string.no_files_found),
                        Toast.LENGTH_LONG).show();

            } else {

                // Get tracks
                int durationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

                do {
                    if (cursor.getInt(durationIndex) > MINIMUM_DURATION) {
                        Track track = getTrackData(cursor);
                        // Add track to list of tracks
                        if (artistName == null || artistName.equals(track.getArtist())) {
                            listOfTracks.add(track);
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();

        } else {

            // Error
            Toast.makeText(context, context.getString(R.string.something_wrong),
                    Toast.LENGTH_LONG).show();
        }

        // Sort list of tracks by name
        Collections.sort(listOfTracks, new Comparator<Track>() {
            public int compare(Track trackA, Track trackB) {
                return trackA.getTitle().compareTo(trackB.getTitle());
            }
        });

        return listOfTracks;
    }

    /**
     * Get list of artist and number of track for each of them
     *
     * @param context - Activity as context
     * @return list of artist
     */
    public static ArrayList<Artist> getArtists(Context context) {

        ArrayList<Artist> artists = new ArrayList<>();

        Uri storageUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = makeCursor(context, storageUri);

        if (cursor != null) {
            if (!cursor.moveToFirst()) {

                // No tracks
                Toast.makeText(context, context.getString(R.string.no_files_found),
                        Toast.LENGTH_LONG).show();

            } else {

                // Get tracks
                int durationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

                do {
                    if (cursor.getInt(durationIndex) > MINIMUM_DURATION) {

                        Track track = getTrackData(cursor);
                        String artistName = track.getArtist();

                        // Add new artist or track for artist to list of artists
                        int index = getArtistIndex(artists, artistName);
                        if (index == NOT_FOUND) {
                            // Add new Artist
                            ArrayList<Track> tracks = new ArrayList<>();
                            tracks.add(track);
                            artists.add(new Artist(artistName, tracks));
                        } else {
                            // Add new track to artist
                            artists.get(index).addTrack(track);
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {

            // Error
            Toast.makeText(context, context.getString(R.string.something_wrong),
                    Toast.LENGTH_LONG).show();
        }

        // Sort list of tracks by name
        Collections.sort(artists, new Comparator<Artist>() {
            public int compare(Artist artistA, Artist artistB) {
                return artistA.getName().compareTo(artistB.getName());
            }
        });

        return artists;
    }

    /**
     * Make cursor for files request
     *
     * @param context    - Activity as context
     * @param storageUri - Media storage uri patch
     * @return cursor for files request
     */
    private static Cursor makeCursor(Context context, Uri storageUri) {
        return context.getContentResolver().query(
                storageUri, // Uri
                null,
                null,
                null,
                null
        );
    }

    /**
     * get and make new Track from media library
     *
     * @param cursor - Cursor
     * @return new Track
     */
    @NonNull
    private static Track getTrackData(Cursor cursor) {

        int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

        String title = cursor.getString(titleIndex);
        String artist = cursor.getString(artistIndex);
        String album = cursor.getString(albumIndex);

        // Return track
        return new Track(title, artist, album);
    }

    /**
     * Define index of artist in array
     *
     * @param artists    - list of artists
     * @param artistName - artist name to define index in array
     * @return - index of artist in array
     */
    private static int getArtistIndex(ArrayList<Artist> artists, String artistName) {

        for (int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getName().equals(artistName)) {
                return i;
            }
        }

        return NOT_FOUND;
    }
}
