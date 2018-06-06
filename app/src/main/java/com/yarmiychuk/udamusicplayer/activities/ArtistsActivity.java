package com.yarmiychuk.udamusicplayer.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yarmiychuk.udamusicplayer.Artist;
import com.yarmiychuk.udamusicplayer.ArtistAdapter;
import com.yarmiychuk.udamusicplayer.MediaPicker;
import com.yarmiychuk.udamusicplayer.R;

import java.util.ArrayList;

public class ArtistsActivity extends AppCompatActivity {

    private ArrayList<Artist> artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Get media files if possible
        artists = MediaPicker.getArtists(this);

        initializeViews();
    }

    private void initializeViews() {

        // ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Define list view and set adapter
        ListView lvTracks = findViewById(R.id.list_view);
        ArtistAdapter adapter = new ArtistAdapter(this, artists);
        lvTracks.setAdapter(adapter);

        // Set onItemClickListener for list items
        lvTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String artistName = artists.get(position).getName();
                Intent artistIntent = new Intent(ArtistsActivity.this, TracksActivity.class);
                artistIntent.putExtra(TracksActivity.ARTIST_INTENT, artistName);
                startActivity(artistIntent);
            }
        });

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

    @Override
    protected void onDestroy() {
        artists.clear();
        super.onDestroy();
    }
}
