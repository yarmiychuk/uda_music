package com.yarmiychuk.udamusicplayer.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yarmiychuk.udamusicplayer.MediaPicker;
import com.yarmiychuk.udamusicplayer.R;
import com.yarmiychuk.udamusicplayer.Track;
import com.yarmiychuk.udamusicplayer.TrackAdapter;

import java.util.ArrayList;

public class TracksActivity extends AppCompatActivity {

    public final static String ARTIST_INTENT = "intent_artist";
    private ArrayList<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Get Artist name from intent if possible
        String artistName = getIntent().getStringExtra(ARTIST_INTENT);

        // Get media files if possible
        tracks = MediaPicker.getTracks(this, artistName);

        initializeViews();

    }

    private void initializeViews() {

        // Action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Define list view and set adapter
        ListView lvTracks = findViewById(R.id.list_view);
        TrackAdapter adapter = new TrackAdapter(this, tracks);
        lvTracks.setAdapter(adapter);

        // Set onItemClickListener for list items
        lvTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get track for selected position
                Track track = tracks.get(position);

                // Make intent
                Intent playIntent = new Intent(TracksActivity.this, PlayerActivity.class);
                playIntent.putExtra(PlayerActivity.INTENT_TITLE, track.getTitle());
                playIntent.putExtra(PlayerActivity.INTENT_ARTIST, track.getArtist());
                playIntent.putExtra(PlayerActivity.INTENT_ALBUM, track.getAlbum());

                // Start activity with intent
                startActivity(playIntent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        tracks.clear();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Go back to previous activity
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
