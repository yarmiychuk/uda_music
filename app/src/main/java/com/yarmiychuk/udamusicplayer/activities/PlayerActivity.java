package com.yarmiychuk.udamusicplayer.activities;

import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yarmiychuk.udamusicplayer.R;

public class PlayerActivity extends AppCompatActivity {

    public static final String INTENT_TITLE = "intent_title";
    public static final String INTENT_ARTIST = "intent_artist";
    public static final String INTENT_ALBUM = "intent_album";

    private boolean isPlaying;

    private TextView tvTrackName, tvArtist, tvAlbum;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initializeViews();

        setCaptions();

        isPlaying = false;
    }

    private void initializeViews() {
        // Action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Album cover
        ImageView ivAlbumCover = findViewById(R.id.iv_album_cover);
        int size = Resources.getSystem().getDisplayMetrics().widthPixels;
        ivAlbumCover.setLayoutParams(new RelativeLayout.LayoutParams(size, size));
        // Track info
        tvTrackName = findViewById(R.id.tv_track_name);
        tvArtist = findViewById(R.id.tv_artist_name);
        tvAlbum = findViewById(R.id.tv_album);
        // Play/Stop button
        btnPlay = findViewById(R.id.button_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    // Stop music
                    btnPlay.setText(getString(R.string.play));
                    isPlaying = false;
                } else {
                    // Play music
                    btnPlay.setText(getString(R.string.stop));
                    Toast.makeText(PlayerActivity.this,
                            getString(R.string.music_is_playing),
                            Toast.LENGTH_SHORT).show();
                    isPlaying = true;
                }
            }
        });
    }

    /**
     * Set track title, artist name and album title to TextViews
     */
    private void setCaptions() {
        tvTrackName.setText(getIntent().getStringExtra(INTENT_TITLE));
        tvArtist.setText(getIntent().getStringExtra(INTENT_ARTIST));
        tvAlbum.setText(getIntent().getStringExtra(INTENT_ALBUM));
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
