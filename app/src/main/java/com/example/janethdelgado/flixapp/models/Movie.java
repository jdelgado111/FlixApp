package com.example.janethdelgado.flixapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    private String posterPath;
    private String title;
    private String overview;
    private String backdropPath;
    private double stars;
    private int movieId;

    //empty constructor needed by the Parceler library
    public Movie(){
    }

    public Movie (JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        stars = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
    }

    //takes JSONArray, iterate through array, generate list of Movies
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++)
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));

        return movies;
    }

    //returns full posterPath URL
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getStars() {
        return stars;
    }

    public Object getMovieId() { return movieId;
    }
}