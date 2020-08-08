package com.rz.movieguide.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rz.movieguide.model.Movie;
import com.rz.movieguide.model.MovieWrapper;
import com.rz.movieguide.repository.remote.MovieApi;
import com.rz.movieguide.repository.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository movieRepository;
    private MutableLiveData<List<Movie>> moviesLiveData;
    private static final String TAG = "MovieRepository";

    public static MovieRepository getMovieRepository() {
        if (movieRepository == null) {
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    private void fetchData() {
        MovieApi movieApi = RetrofitClient.getMovieApi();
        Log.d(TAG, "onCreate: ");
        Call<MovieWrapper> movies = movieApi.popularMovies(5);
        movies.enqueue(new Callback<MovieWrapper>() {
                           @Override
                           public void onResponse(Call<MovieWrapper> call, Response<MovieWrapper> response) {
                               Log.d(TAG, response.toString());
                               Log.d(TAG, response.body().toString());
                               moviesLiveData.setValue(response.body().getMovies());
                           }

                           @Override
                           public void onFailure(Call<MovieWrapper> call, Throwable t) {
                               Log.d(TAG, "Failed!!!");
                               t.printStackTrace();
                           }
                       }
        );
    }

    private LiveData<List<Movie>> getMovies() {
        return moviesLiveData;
    }


}
