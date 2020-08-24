package com.rz.movieguide.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.rz.movieguide.R;
import com.rz.movieguide.view.details.MovieDetailsFragment;
import com.rz.movieguide.view.list.MovieListFragment;
import com.rz.movieguide.viewmodel.MovieListViewModel;

public class MovieActivity extends AppCompatActivity {
    private static final String TAG = "MovieActivity";
    private MovieListViewModel movieListViewModel;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setUpToolbar();

        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            MovieListFragment movieListFragment = new MovieListFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, movieListFragment).commit();
        }

        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        movieListViewModel.getSelectedMovie().observe(this, movie -> openDetailsFragment());
    }

    public void openDetailsFragment() {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, movieDetailsFragment).addToBackStack("temp").commit();
        toolbar.setVisibility(View.INVISIBLE);
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("MovieGuide");
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular_movies:
                Log.d(TAG, "onOptionsItemSelected: Popular movies selected");
                item.setChecked(true);
                movieListViewModel.setListType("popular");
                return true;
            case R.id.top_rated_movies:
                Log.d(TAG, "onOptionsItemSelected: top_rated_movies movies selected");
                item.setChecked(true);
                movieListViewModel.setListType("toprated");
                return true;
            case R.id.new_movies:
                Log.d(TAG, "onOptionsItemSelected: new_movies movies selected");
                item.setChecked(true);
                movieListViewModel.setListType("newest");
                return true;
//            case R.id.favorite:
//                Log.d(TAG, "onOptionsItemSelected: favorite movies selected");
//                item.setChecked(true);
//                movieListViewModel.setListType("favorite");
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}