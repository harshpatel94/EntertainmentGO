package com.example.hunter.finalproj;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by HUNTER on 4/23/2017.
 */

public class GetRequest {

    public  GetRequest(String url,MoviesAdapter moviesAdapter){
        ArrayList<Movies> arrayList;
        try {
            URL json = new URL(url);
            URLConnection tc = json.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    tc.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                JSONArray ja = new JSONArray(line);
                Log.d("String Passed>>",ja.toString());
                arrayList = Movies.fromJson(ja);
                for(Movies movie:arrayList){
                    moviesAdapter.add(movie);
                }
                moviesAdapter.notifyDataSetChanged();
            }
        } catch (IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
