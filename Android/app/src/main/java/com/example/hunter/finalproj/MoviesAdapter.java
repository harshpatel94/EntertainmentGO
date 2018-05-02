package com.example.hunter.finalproj;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HUNTER on 4/20/2017.
 */

public class MoviesAdapter extends ArrayAdapter<Movies> {

    public MoviesAdapter(Context context, ArrayList<Movies> arrayList){
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        // Get the data item for this position
        Movies movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movies, parent, false);
        }
        // Lookup views within item layout
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        TextView tvCast = (TextView) convertView.findViewById(R.id.tvCast);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
        // Populate the data into the template view using the data object

        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText("Score: " + movie.getImdbrating());
        tvCast.setText(movie.getActors());
        Picasso.with(getContext()).load(movie.getPoster()).placeholder(R.drawable.small_movie_poster).into(ivPosterImage);
        float value = (float) (movie.getAverageRating());
        ratingBar.setRating(value);
        // Return the completed view to render on screen
        return convertView;
    }
}
