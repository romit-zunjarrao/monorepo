package com.rz.movieguide.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;


import com.rz.movieguide.R;
import com.rz.movieguide.view.details.MovieDetailsFragment;
import com.rz.movieguide.view.list.MovieListFragment;
import com.rz.movieguide.viewmodel.MovieListViewModel;

public class MovieActivity extends AppCompatActivity {
    private static final String TAG = "MovieActivity";
    private MovieListViewModel movieListViewModel;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        if(fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        if(fragmentManager.findFragmentById(R.id.fragment_container) == null){
            MovieListFragment movieListFragment = new MovieListFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container,movieListFragment).commit();
        }

        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        movieListViewModel.getSelectedMovie().observe(this,movie -> openDetailsFragment());
    }

    public void openDetailsFragment() {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,movieDetailsFragment).addToBackStack("temp").commit();
    }
}