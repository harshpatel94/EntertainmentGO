package com.example.hunter.finalproj;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HUNTER on 4/20/2017.
 */

public class Movies implements Serializable {


    private String titleId;
    private String title;
    private int year;
    private double averageRating;
    private String rated;
    private String released;
    private String runtime;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    private String metascore;
    private double imdbrating;
    private int imdbvotes;
    private String imdbID;
    private String type;
    private String addedOn;
    private ArrayList<String> genreList;




    public Movies(){}



    public static Movies fromJson(JSONObject jsonObject){
        Movies m = new Movies();
        try{

            m.titleId = jsonObject.getString("titleId");
            m.title = jsonObject.getString("title");
            m.year = jsonObject.getInt("year");
            m.averageRating = jsonObject.getDouble("averageRating");
            m.rated = jsonObject.getString("rated");
            m.released = jsonObject.getString("released");
            m.runtime = jsonObject.getString("runtime");
            m.director = jsonObject.getString("director");
            m.writer = jsonObject.getString("writers");
            m.actors = jsonObject.getString("actors");
            m.plot = jsonObject.getString("plot");
            m.language = jsonObject.getString("language");
            m.country = jsonObject.getString("country");
            m.awards = jsonObject.getString("awards");
            m.poster = jsonObject.getString("poster");
            m.metascore = jsonObject.getString("metascore");
            m.imdbrating = jsonObject.getDouble("imdbRating");
            m.imdbvotes = jsonObject.getInt("imdbVotes");
            m.imdbID = jsonObject.getString("imdbID");
            m.type = jsonObject.getString("type");
            m.addedOn = jsonObject.getString("addedOn");
            //JSONArray genereList = jsonObject.getJSONArray("genre");
//            for(int i=0;i<genereList.length();i++) {
//                m.genreList.add(genereList.getJSONObject(i).getString("genre"));
//            }
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return m;
    }


    public static ArrayList<Movies> fromJson(JSONArray jsonArray){
        ArrayList<Movies> movies = new ArrayList<Movies>(jsonArray.length());

        for(int i=0;i<jsonArray.length();i++){
            JSONObject moviesJson = null;
            try{
                moviesJson = jsonArray.getJSONObject(i);
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

            Movies movie = Movies.fromJson(moviesJson);
            if(movie != null){
                movies.add(movie);
            }
        }

        return movies;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public double getImdbrating() {
        return imdbrating;
    }

    public void setImdbrating(double imdbrating) {
        this.imdbrating = imdbrating;
    }

    public int getImdbvotes() {
        return imdbvotes;
    }

    public void setImdbvotes(int imdbvotes) {
        this.imdbvotes = imdbvotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getGenreList() {
        return TextUtils.join(", ",genreList);
    }

    public void setGenreList(ArrayList<String> genreList) {
        this.genreList = genreList;
    }
}
