package com.example.hunter.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HUNTER on 4/24/2017.
 */

public class SortMovies extends Fragment {

    public static final String MOVIE_DETAIL_KEY = "movie";
    private ListView lvMovies;
    private MoviesAdapter adapterMovies;
    ArrayList<Movies> arrayList = new ArrayList<Movies>();


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        StrictMode.enableDefaults();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final View rootView = inflater.inflate(R.layout.layout_toprate_movies, container, false);

        lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        adapterMovies = new MoviesAdapter(getContext(),arrayList);
        lvMovies.setAdapter(adapterMovies);
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),MovieDetail.class);
                intent.putExtra(MOVIE_DETAIL_KEY, (Serializable) adapterMovies.getItem(position));
                startActivity(intent);
            }
        });
        String urlstring = getArguments().getString("URL2");
        new GetRequest(urlstring,adapterMovies);

        return rootView;

    }
}