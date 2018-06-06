package com.yarmiychuk.udamusicplayer;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DmitryYarmiychuk on 02.04.2018.
 * Создал DmitryYarmiychuk 02.04.2018
 */

public class TrackAdapter extends ArrayAdapter<Track> {

    private final String LOG_TAG = "TrackAdapter";

    /**
     * Constructor of adapter.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param tracks  A List of track objects to display in a list
     */
    public TrackAdapter(Activity context, ArrayList<Track> tracks) {
        super(context, 0, tracks);
    }

    /**
     * Provides a view for an AdapterView
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Define TextViews
        TextView tvTitle = itemView.findViewById(R.id.tv_title);
        TextView tvSubtitle = itemView.findViewById(R.id.tv_subtitle);

        // Get item for current position
        Track track = getItem(position);

        // Set text to textViews
        tvTitle.setText(track.getTitle());
        tvSubtitle.setText(track.getArtist());

        return itemView;
    }
}
