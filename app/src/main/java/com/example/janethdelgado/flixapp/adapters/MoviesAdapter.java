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
import com.bumptech.glide.request.RequestOptions;
import com.example.janethdelgado.flixapp.R;
import com.example.janethdelgado.flixapp.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;
    final int MOVIE = 0, IMAGE = 1;
    final double POPULAR = 7.0;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // tells RecyclerView how many items are in dataset
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // returns whether to display movie info or image
    @Override
    public int getItemViewType(int position) {
        Movie movie = movies.get(position);
        double stars = movie.getStars();

        //if movie is popular return IMAGE, otherwise return MOVIE
        if (stars > POPULAR)
            return IMAGE;
        else
            return MOVIE;
    }

    // responsible for inflating item_movie.xml View
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("smile", "onCreateViewHolder");

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (i == IMAGE)
        {
            View vImage = inflater.inflate(R.layout.item_image, viewGroup, false);
            viewHolder = new ViewHolderImage(vImage);
        }
        else
        {
            View vMovie = inflater.inflate(R.layout.item_movie, viewGroup, false);
            viewHolder = new ViewHolderMovie(vMovie);
        }
        //View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        //return new ViewHolderMovie(view); // creates/returns new ViewHolder (using constructor below)
                                          // and uses this View as parameter
        return viewHolder;
    }

    // attach data (at position i in data set) to particular ViewHolder (given by adapter)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("smile", "onBindViewHolder " + i);

        Movie movie = movies.get(i);

        if (viewHolder.getItemViewType() == IMAGE)
        {
            ViewHolderImage vhImage = (ViewHolderImage) viewHolder;
            vhImage.setIvBackdrop(movie);
        }
        else {
            ViewHolderMovie vhMovie = (ViewHolderMovie) viewHolder; //cast viewHolder to VHMovie
            //bind the movie data into the viewHolder
            vhMovie.bind(movie); // use custom bind() method to populate movie data
        }
    }



    //this ViewHolder represents our item_movie.xml
    // (holds the view that will be recycled i.e. the individual movies)
    class ViewHolderMovie extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolderMovie(@NonNull View itemView) {
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

            Glide.with(context).load(imageUrl).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(ivPoster);
        }
    }

    
    class ViewHolderImage extends RecyclerView.ViewHolder {

        ImageView ivBackdrop;

        public ViewHolderImage(@NonNull View itemView) {
            super(itemView);
            ivBackdrop = itemView.findViewById(R.id.ivBackdrop);
        }

        //puts image into viewHolder
        public void setIvBackdrop(Movie movie) {
            String imageUrl = movie.getBackdropPath();
            Glide.with(context).load(imageUrl).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(ivBackdrop);
        }
    }
}