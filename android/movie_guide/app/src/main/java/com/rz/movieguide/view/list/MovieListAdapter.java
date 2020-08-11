package com.rz.movieguide.view.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rz.movieguide.R;
import com.rz.movieguide.model.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListViewHolder> {

    private List<Movie> movies;
    private Context context;
    private MovieListFragment movieListFragment;

    public static final String BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342";

    public MovieListAdapter(List<Movie> movies, MovieListFragment fragment) {
        this.movies = movies;
        this.movieListFragment = fragment;
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View view = inflater.inflate(R.layout.movie_list_element, parent, false);
        MovieListViewHolder viewHolder = new MovieListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(context)
                .asBitmap()
                .load(movie.getPosterPath())
                .centerCrop()
                .into(new BitmapImageViewTarget(holder.movieImage) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(resource, transition);
                        Palette.from(resource).generate(palette -> {
                            holder.titleBackground.setBackgroundColor(palette.getVibrantColor(context
                                    .getResources().getColor(R.color.black_translucent_60)));
                        });
                    }
                });
        holder.movieTitle.setText(movie.getTitle());
        holder.itemView.setOnClickListener(view -> {
            movieListFragment.onMovieClicked(movie);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
