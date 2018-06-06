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
 * Created by DmitryYarmiychuk on 15.04.2018.
 * Создал DmitryYarmiychuk 15.04.2018
 */

public class ArtistAdapter extends ArrayAdapter<Artist> {

    /**
     * Constructor of adapter.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param artists A List of artist objects to display in a list
     */
    public ArtistAdapter(Activity context, ArrayList<Artist> artists) {
        super(context, 0, artists);
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

        // Set text to textViews
        tvTitle.setText(getItem(position).getName());
        String tracksCount = getContext().getString(R.string.number_of_tracks) + " " +
                getItem(position).getTracksCount();
        tvSubtitle.setText(tracksCount);

        return itemView;
    }
}
