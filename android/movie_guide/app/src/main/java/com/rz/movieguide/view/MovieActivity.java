package com.rz.movieguide.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.rz.movieguide.R;
import com.rz.movieguide.viewmodel.MovieListViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MovieListViewModel movieListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        movieListViewModel.getMovieList().observe(this,movies -> {
            Log.d(TAG,movies.toString());
        });
    }
}