package com.example.janethdelgado.flixapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.janethdelgado.flixapp.DetailActivity;
import com.example.janethdelgado.flixapp.R;
import com.example.janethdelgado.flixapp.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;
    final int MOVIE = 0, IMAGE = 1;
    final double POPULAR = 7.0;
    int radius = 40; // corner radius, higher value = more rounded
    int margin = 10; // crop margin, set to 0 for corners with no crop

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
        if (stars >= POPULAR)
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

        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;
        @BindView(R.id.ivPoster) ImageView ivPoster;
        @BindView(R.id.container_movie) RelativeLayout container_movie;

        public ViewHolderMovie(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //puts data into viewHolder
        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl = movie.getPosterPath();

            // reference backdrop path only if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageUrl = movie.getBackdropPath();

            Glide.with(context).load(imageUrl).apply(new RequestOptions().transform(new RoundedCornersTransformation(radius, margin)).placeholder(R.drawable.placeholder)).into(ivPoster);

            //add click listener on the whole row
            container_movie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //navigate to detail activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }

    //this ViewHolder represents item_image.xml
    class ViewHolderImage extends RecyclerView.ViewHolder {

        @BindView(R.id.ivBackdrop) ImageView ivBackdrop;
        @BindView(R.id.container_image) RelativeLayout container_image;

        public ViewHolderImage(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //puts image into viewHolder
        public void setIvBackdrop(final Movie movie) {
            String imageUrl = movie.getBackdropPath();
            Glide.with(context).load(imageUrl).apply(new RequestOptions().transform(new RoundedCornersTransformation(radius, margin)).placeholder(R.drawable.placeholder)).into(ivBackdrop);

            //add click listener on whole image (layout)
            container_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //navigate to detail activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}