package com.rz.movieguide.view.details;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rz.movieguide.R;
import com.rz.movieguide.model.Movie;
import com.rz.movieguide.model.Review;
import com.rz.movieguide.model.Video;
import com.rz.movieguide.viewmodel.MovieListViewModel;

import java.util.List;


public class MovieDetailsFragment extends Fragment {
    private static final String TAG = "MovieDetailsFragment";

    MovieListViewModel movieListViewModel;

    TextView movieName;
    TextView movieYear;
    TextView movieRating;
    TextView movieDescription;
    ImageView moviePoster;
    CollapsingToolbarLayout collapsingToolbar;

    TextView trailersLabel;
    LinearLayout trailersLayout;
    HorizontalScrollView trailersContainer;

    TextView reviewLabel;
    LinearLayout reviewLayout;

    FloatingActionButton favoriteButton;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieName = view.findViewById(R.id.movie_name);
        movieYear = view.findViewById(R.id.movie_year);
        movieRating = view.findViewById(R.id.movie_rating);
        movieDescription = view.findViewById(R.id.movie_description);
        moviePoster = view.findViewById(R.id.movie_poster);
        collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        setCollapsingToolbarLayout();

        trailersLabel = view.findViewById(R.id.trailer_label);
        trailersLayout = view.findViewById(R.id.trailers);
        trailersContainer = view.findViewById(R.id.trailers_container);

        reviewLabel = view.findViewById(R.id.review_label);
        reviewLayout = view.findViewById(R.id.reviews);

//        favoriteButton = view.findViewById(R.id.favorite);
//        favoriteButton.setOnClickListener(handleFavorite());

        movieListViewModel = ViewModelProviders.of(requireActivity()).get(MovieListViewModel.class);
        movieListViewModel.getSelectedMovie().observe(this, movie -> {
            // Log.d(TAG, movie.toString());
            fillMovieDetails(movie);
        });

        movieListViewModel.getTrailers().observe(this, videos -> {
//            Log.d(TAG, "onViewCreated: "+videos);
            fillVideoDetails(videos);
        });

        movieListViewModel.getReviews().observe(this, reviews -> {
//            Log.d(TAG, "onViewCreated: "+reviews);
            fillReviewDetails(reviews);
        });
    }

    public void setCollapsingToolbarLayout() {
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle("Movie Details");
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);
    }

    public void fillMovieDetails(Movie movie) {
        Glide.with(this)
                .load(movie.getBackdropPath())
                .into(moviePoster);
        movieName.setText(movie.getTitle());
        movieYear.setText(String.format("Release Date  : %s", movie.getReleaseDate()));
        movieDescription.setText(movie.getOverview());
        movieRating.setText(String.format("%.1f/10", movie.getVoteAverage()));
    }

    public void fillVideoDetails(List<Video> videos) {
        Log.d(TAG, "fillVideoDetails: method called");
        if (videos.isEmpty()) {
            Log.d(TAG, "fillVideoDetails: No Video");
            trailersLabel.setVisibility(View.GONE);
            trailersLayout.setVisibility(View.GONE);
            trailersContainer.setVisibility(View.GONE);
        } else {
            trailersLabel.setVisibility(View.VISIBLE);
            trailersLayout.setVisibility(View.VISIBLE);
            trailersContainer.setVisibility(View.VISIBLE);

            this.trailersLayout.removeAllViews();
            LayoutInflater inflater = getActivity().getLayoutInflater();

            for (Video video : videos) {
                View thumbnail = inflater.inflate(R.layout.video, this.trailersLayout, false);
                thumbnail.setOnClickListener(view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getUrl()));
                    startActivity(intent);
                });
                ImageView videoThumbnail = thumbnail.findViewById(R.id.video_thumbnail);
                Glide.with(this)
                        .load(video.getThumbnailUrl())
                        .into(videoThumbnail);
                Log.d(TAG, "fillVideoDetails: " + video.getThumbnailUrl());
                this.trailersLayout.addView(thumbnail);
            }
        }
    }

    public void fillReviewDetails(List<Review> reviews) {
        if (reviews.isEmpty()) {
            reviewLabel.setVisibility(View.GONE);
            reviewLayout.setVisibility(View.GONE);
        } else {
            reviewLabel.setVisibility(View.VISIBLE);
            reviewLayout.setVisibility(View.VISIBLE);

            this.reviewLayout.removeAllViews();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            for (Review review : reviews) {
                View reviewView = inflater.inflate(R.layout.review, this.reviewLayout, false);
                TextView authorName = reviewView.findViewById(R.id.review_author);
                TextView content = reviewView.findViewById(R.id.review_content);
                content.setOnClickListener(view -> {
                    if (content.getMaxLines() == 5) {
                        content.setMaxLines(500);
                    } else {
                        content.setMaxLines(5);
                    }
                });
                authorName.setText(review.getAuthor());
                content.setText(review.getContent());
                this.reviewLayout.addView(reviewView);
            }
        }
    }


    public View.OnClickListener handleFavorite() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                movieListViewModel.addFavorite();
            }
        };
    }


}