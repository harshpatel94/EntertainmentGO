package com.example.hunter.finalproj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by HUNTER on 4/20/2017.
 */

public class MovieDetail extends Activity {
    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvPlot;
    private TextView tvActor;
    private TextView tvGenre;
    private RatingBar userRating;
    private RatingBar avgRating;
    private TextView tvImdbScore;
    private ImageButton imageButton;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String S_userId = "userId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.layout_movies_detail);
        // Fetch views
        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPlot = (TextView) findViewById(R.id.tvPlot);
        tvActor = (TextView) findViewById(R.id.tvCast);
        userRating =  (RatingBar) findViewById(R.id.userRating);
        tvImdbScore = (TextView) findViewById(R.id.tvImdbScore);
        tvGenre = (TextView) findViewById(R.id.tvGenre);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        avgRating = (RatingBar) findViewById(R.id.avgRating);
        // Use the movie to populate the data into our views
        final Movies movie = (Movies) getIntent().getSerializableExtra(MoviesFragment.MOVIE_DETAIL_KEY);
        loadMovie(movie);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.setBackgroundResource(R.drawable.heart_full);
                SaveMovie(movie);
            }
        });

        avgRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                SendRating(rating,movie);
                ratingBar.setRating(rating);
            }
            
        });
    }

    public void loadMovie(Movies movie) {
        // Populate data
        tvTitle.setText(movie.getTitle());
        tvGenre.setText(Html.fromHtml("<b >User Rating: </b> "));
        float value = (float) (movie.getAverageRating());
        userRating.setRating(value);
        tvImdbScore.setText(Html.fromHtml("<b>IMDB Rating:</b> " + movie.getImdbrating()));
        tvActor.setText(Html.fromHtml("<b>Cast:</b><br> " + movie.getActors()));
        tvPlot.setText(Html.fromHtml("<b>Description:</b><br><br> " + movie.getPlot()));
        // R.drawable.large_movie_poster from
        // http://content8.flixster.com/movie/11/15/86/11158674_pro.jpg -->
        Picasso.with(this).load(movie.getPoster()).
                placeholder(R.drawable.large_movie_poster).
                into(ivPosterImage);
    }

    public void SaveMovie(Movies movies) {

        String url = "http://10.0.2.2:8080/wishlists";
        JSONObject ja = new JSONObject();
        JSONObject jb = new JSONObject();
        JSONObject jc = new JSONObject();
        String titleId = movies.getTitleId();
        Log.d("String title>>>", titleId);
        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String userid = sharedpreferences.getString(S_userId,"Missing");

        String addedon = movies.getAddedOn();
        try {
            jb.put("titleId", titleId);
            ja.put("title", jb);
            jc.put("userId", userid);
            ja.put("user", jc);
            ja.put("addedOn", addedon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON String>>>>", ja.toString());

//        final ProgressDialog pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                url, ja,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response>>>", response.toString());
                        //pDialog.hide();
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("String:>>>", "Error: " + error.getMessage());
                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 403:
//                            json = new String(response.data);
//                            json = trimMessage(json, "error");
//                            if(json != null) displayMessage(json);
                            break;
                        case 302:
                            Toast.makeText(getApplicationContext(),"Already Added to wish list.",Toast.LENGTH_LONG).show();
                    }
                }
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                //pDialog.hide();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }

    public void SendRating(float rat,Movies movies){
        String url = "http://10.0.2.2:8080/ratings";
        JSONObject ja = new JSONObject();
        JSONObject jb = new JSONObject();
        JSONObject jc = new JSONObject();
        String titleId = movies.getTitleId();
        Log.d("String title>>>", titleId);
        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String userid = sharedpreferences.getString(S_userId,"Missing");
        String date = DateFormat.getDateInstance().format(new Date());

        try {
            ja.put("rating",rat);
            jb.put("titleId", titleId);
            ja.put("title", jb);
            jc.put("userId", userid);
            ja.put("user", jc);
            ja.put("date",date);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON String>>>>", ja.toString());

//        final ProgressDialog pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                url, ja,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response>>>", response.toString());
                        //pDialog.hide();
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("String:>>>", "Error: " + error.getMessage());
                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 403:
//                            json = new String(response.data);
//                            json = trimMessage(json, "error");
//                            if(json != null) displayMessage(json);
                            break;
                        case 302:
                            Toast.makeText(getApplicationContext(),"Already Added to wish list.",Toast.LENGTH_LONG).show();
                    }
                }
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                //pDialog.hide();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);


    }
}
