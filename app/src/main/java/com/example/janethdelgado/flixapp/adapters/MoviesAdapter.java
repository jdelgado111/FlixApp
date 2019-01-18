package com.example.janethdelgado.flixapp.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.janethdelgado.flixapp.R;
import com.example.janethdelgado.flixapp.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // responsible for inflating item_movie.xml View
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("smile", "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view); // creates/returns new ViewHolder (using constructor below)
                                    // and uses this View as parameter
    }

    // attach data (at position i in dataset) to particular viewholder (given by adapter)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("smile", "onBindViewHolder " + i);
        Movie movie = movies.get(i);

        //bind the movie data into the viewholder
        //viewHolder.tvTitle.setText("hello");
        viewHolder.bind(movie); // create custom bind() method to populate movie data
    }

    // tells RecyclerView how many items are in dataset
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //this viewholder represents our item_movie.xml
    // (holds the view that will be recycled i.e. the individual movies)
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        //puts data into viewHolder
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl = movie.getPosterPath();

            // reference backdrop path only if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageUrl = movie.getBackdropPath();

            Glide.with(context).load(imageUrl).into(ivPoster);
        }
    }
}