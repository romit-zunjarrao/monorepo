package com.rz.movieguide.view.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rz.movieguide.R;

public class MovieListViewHolder extends RecyclerView.ViewHolder {
    View layout;
    ImageView movieImage;
    View titleBackground;
    TextView movieTitle;

    public MovieListViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView;
        movieImage = itemView.findViewById(R.id.movie_image_element);
        titleBackground = itemView.findViewById(R.id.movie_title_background);
        movieTitle = itemView.findViewById(R.id.movie_title_element);
    }

}
