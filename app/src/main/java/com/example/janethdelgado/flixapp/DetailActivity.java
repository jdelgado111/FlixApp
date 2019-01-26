package com.example.janethdelgado.flixapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.janethdelgado.flixapp.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyArTseTzE5T8XPQl_gHIKjmZhDPEDmYFCw";
    // %d is the movie ID
    private static final String TRAILERS_API = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    final double POPULAR = 7.0;

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;
    TextView tvLang;
    TextView tvDate;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);
        tvLang = findViewById(R.id.tvLang);
        tvDate = findViewById(R.id.tvDate);

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getStars());

        //include more detail/info like release date, language, etc
        String language = movie.getLanguage();
        tvLang.setText("Language: " + language.toUpperCase());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //Date date = null;
        try {
            Date date = formatter.parse(movie.getDate());
            formatter = new SimpleDateFormat("EEE MMM dd, yyyy");
            String dateText = formatter.format(date);
            tvDate.setText("Release Date: " + dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(TRAILERS_API, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray results = response.getJSONArray("results");

                    ///error check: if no results, return early
                    if (results.length() == 0)
                        return;

                    //this assumes the site we get the trailer from is YT
                    //can do additional error checking:
                    // check "site" in JSON is YT, if not, movie to next index in array
                    JSONObject movieTrailer = results.getJSONObject(0);
                    String youTubeKey = movieTrailer.getString("key");
                    initializeYoutube(youTubeKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void initializeYoutube(final String youTubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("smile", "on init success");

                if (movie.getStars() >= POPULAR)
                    youTubePlayer.loadVideo(youTubeKey);
                else
                    youTubePlayer.cueVideo(youTubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("smile", "on init failure");
            }
        });
    }
}
