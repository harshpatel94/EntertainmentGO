package com.example.hunter.finalproj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HUNTER on 4/24/2017.
 */

public class WishList extends Fragment {

    public static final String MOVIE_DETAIL_KEY = "movie";
    private ListView lvMovies;
    private MoviesAdapter adapterMovies;
    ArrayList<Movies> arrayList = new ArrayList<Movies>();
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String S_userId = "userId";


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





        new GetRequest("http://10.0.2.2:8080/wishlists/5fe8437d-505b-4c0c-9233-0fa3f23af682",adapterMovies);
//        String url = "http://10.0.2.2:8080/wishlists/"+userid;
//        JsonArrayRequest jsonRequest = new JsonArrayRequest
//                (url,new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // the response is already constructed as a JSONObject!
//                        JSONArray ja = null;
//                        try {
//                            ja = new JSONArray(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        arrayList = Movies.fromJson(ja);
//                        for(Movies movie:arrayList){
//                            adapterMovies.add(movie);
//                        }
//                        adapterMovies.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                });
//
//
//        Volley.newRequestQueue(getActivity()).add(jsonRequest);


//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();
//
//        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        String userid = sharedpreferences.getString(S_userId,"Missing");
//        String url = "http://10.0.2.2:8080/wishlists/5fe8437d-505b-4c0c-9233-0fa3f23af682";
//
//        StringRequest strReq = new StringRequest(Request.Method.GET,
//                url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d("Response String>>", response);
//                pDialog.hide();
//                JSONArray ja = null;
//                        try {
//                            ja = new JSONArray(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        arrayList = Movies.fromJson(ja);
//                        for(Movies movie:arrayList){
//                            adapterMovies.add(movie);
//                        }
//                        adapterMovies.notifyDataSetChanged();
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Error String>>", "Error: " + error.getMessage());
//                pDialog.hide();
//            }
//        });
//
//        Volley.newRequestQueue(getActivity()).add(strReq);
        return rootView;

    }
}

