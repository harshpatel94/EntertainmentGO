package com.example.hunter.finalproj;


import android.content.Intent;

import android.os.Bundle;

import android.os.StrictMode;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.Serializable;



/**
 * Created by HUNTER on 4/19/2017.
 */

public class MoviesFragment extends Fragment{

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

        try {
            URL json = new URL(
                    "http://10.0.2.2:8080/titles/topTiles/movie");
            URLConnection tc = json.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    tc.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                JSONArray ja = new JSONArray(line);
                arrayList = Movies.fromJson(ja);
                for(Movies movie:arrayList){
                    adapterMovies.add(movie);
                }
                adapterMovies.notifyDataSetChanged();
            }
        } catch (IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rootView;

    }
}
