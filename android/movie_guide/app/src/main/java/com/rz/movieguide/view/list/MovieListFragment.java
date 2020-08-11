package com.rz.movieguide.view.list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rz.movieguide.R;
import com.rz.movieguide.model.Movie;
import com.rz.movieguide.viewmodel.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends Fragment {
    private MovieListViewModel movieListViewModel;
    private static final String TAG = "MovieListFragment";
    private RecyclerView recyclerView;
    private List<Movie> movies = new ArrayList<>();

    public MovieListFragment() {
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
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.movie_list);
        MovieListAdapter adapter = new MovieListAdapter(movies,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        movieListViewModel = ViewModelProviders.of(requireActivity()).get(MovieListViewModel.class);
        movieListViewModel.getMovieList().observe(this, movies -> {
            this.movies.addAll(movies);
            adapter.notifyDataSetChanged();
        });
    }

    public void onMovieClicked(Movie movie) {
        Log.d(TAG,movie.toString());
        movieListViewModel.setSelectedMovie(movie);
    }

}