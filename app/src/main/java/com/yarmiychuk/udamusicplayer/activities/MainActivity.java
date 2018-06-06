package com.yarmiychuk.udamusicplayer.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yarmiychuk.udamusicplayer.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int PERMISSION_READ_STORAGE = 10;

    private LinearLayout llMenu;
    private TextView tvPermissionMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        checkUserPermission();
    }

    private void initializeViews() {
        llMenu = findViewById(R.id.ll_menu);
        tvPermissionMessage = findViewById(R.id.tv_permission_message);
        tvPermissionMessage.setVisibility(View.INVISIBLE);
        tvPermissionMessage.setOnClickListener(this);
        findViewById(R.id.tv_tracks).setOnClickListener(this);
        findViewById(R.id.tv_artists).setOnClickListener(this);
    }

    /**
     * The method checks for permission.
     * If necessary, shows the permission request dialog.
     */
    private void checkUserPermission() {
        if (!isPermissionGranted()) {
            showPermissionRequest();
        }
        invalidateUI();
    }

    /**
     * Get state of grant permission for read files
     *
     * @return true, if permission granted
     */
    private boolean isPermissionGranted() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * The method for selecting how to display the permission request dialog
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void showPermissionRequest() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showDialog();
        } else {
            showRequest();
        }

    }

    /**
     * The method creates and displays an information dialog about need for permission
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.permission_message));
        builder.setTitle(getString(R.string.permission_title));
        builder.setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showRequest();
            }
        });
        builder.setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvPermissionMessage.setVisibility(View.VISIBLE);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Shows system permission request
     */
    private void showRequest() {
        ActivityCompat.requestPermissions(
                MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_READ_STORAGE
        );
    }

    /**
     * Listener for result of permission requests
     *
     * @param requestCode  - code for permission's request
     * @param permissions  - list of requested permissions
     * @param grantResults - result of permission request
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STORAGE:
                if (tvPermissionMessage.getVisibility() == View.VISIBLE) {
                    tvPermissionMessage.setText(getString(R.string.permission_required));
                }
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    invalidateUI();
                } else {
                    tvPermissionMessage.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    /**
     * Shows navigation menu if permission granted or hide it
     */
    private void invalidateUI() {
        if (isPermissionGranted()) {
            llMenu.setVisibility(View.VISIBLE);
            tvPermissionMessage.setVisibility(View.INVISIBLE);
        } else {
            llMenu.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_permission_message:
                showRequest();
                break;
            case R.id.tv_tracks:
                startActivity(new Intent(MainActivity.this, TracksActivity.class));
                break;
            case R.id.tv_artists:
                startActivity(new Intent(MainActivity.this, ArtistsActivity.class));
                break;
        }
    }
}
