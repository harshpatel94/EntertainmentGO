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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by HUNTER on 4/24/2017.
 */

public class MainMovieFragment extends Fragment {

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



        new GetRequest("http://10.0.2.2:8080/titles/filter/type/movie",adapterMovies);

        return rootView;

    }
}
