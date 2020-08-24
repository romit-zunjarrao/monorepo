package com.rz.movieguide.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rz.movieguide.model.Movie;
import com.rz.movieguide.model.MovieWrapper;
import com.rz.movieguide.model.Review;
import com.rz.movieguide.model.ReviewWrapper;
import com.rz.movieguide.model.Video;
import com.rz.movieguide.model.VideoWrapper;
import com.rz.movieguide.repository.local.MovieDao;
import com.rz.movieguide.repository.local.MovieRoomDatabase;
import com.rz.movieguide.repository.remote.MovieApi;
import com.rz.movieguide.repository.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final String TAG = "MovieRepository";
    private static MovieRepository movieRepository;
    private MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
    private MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();
    private MutableLiveData<List<Video>> trailersLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Review>> reviewLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> changedListType = new MutableLiveData<>();
    private String listType = "popular";
    private int moviePageNumber = 1;
    private Application application;
    private MovieRoomDatabase movieRoomDatabase;
    private MovieDao movieDao;

    MovieApi movieApi;

    public MovieRepository(Application application) {
        movieApi = RetrofitClient.getMovieApi();
        this.application = application;
        movieRoomDatabase = MovieRoomDatabase.getMovieRoomDatabaseInstance(application);
        movieDao = movieRoomDatabase.movieDao();
    }

    public static MovieRepository getMovieRepository(Application application) {
        if (movieRepository == null) {
            movieRepository = new MovieRepository(application);
        }
        return movieRepository;
    }

    private void fetchMovieData() {
        Log.d(TAG, "onCreate: ");

        if (!listType.equals("favorite")) {
            Call<MovieWrapper> movies = null;
            switch (this.listType) {
                case "toprated":
                    movies = movieApi.topRatedMovies(moviePageNumber);
                    break;
                case "newest":
                    movies = movieApi.nowPlaying(moviePageNumber);
                    break;
                case "popular":
                default:
                    movies = movieApi.popularMovies(moviePageNumber);
                    break;
            }


//        Call<MovieWrapper> movies = movieApi.popularMovies(moviePageNumber);
            movies.enqueue(new Callback<MovieWrapper>() {
                               @Override
                               public void onResponse(Call<MovieWrapper> call, Response<MovieWrapper> response) {
//                                   Log.d(TAG, response.toString());
//                                   Log.d(TAG, "onResponse: " + listType);
//                                   Log.d(TAG, response.body().toString());
                                   moviesLiveData.setValue(response.body().getMovies());
                               }

                               @Override
                               public void onFailure(Call<MovieWrapper> call, Throwable t) {
                                   Log.d(TAG, "Failed!!!");
                                   t.printStackTrace();
                               }
                           }
            );
        } else {
//            moviesLiveData.setValue(this.getAllFavoriteMovies());
            MovieRoomDatabase.databaseWriterExecutor.execute(() -> {
                        moviesLiveData.postValue(movieDao.getAllFavoriteMovies());
                    }
            );
        }
    }

    private void fetchTrailer() {
        Call<VideoWrapper> trailers = movieApi.trailers(selectedMovie.getValue().getId(), 1);
        trailers.enqueue(new Callback<VideoWrapper>() {
            @Override
            public void onResponse(Call<VideoWrapper> call, Response<VideoWrapper> response) {
//                Log.d(TAG, "onResponse: trailer " + response.toString());
                trailersLiveData.setValue(response.body().getVideos());
            }

            @Override
            public void onFailure(Call<VideoWrapper> call, Throwable t) {
                Log.d(TAG, "onFailure: Video Failed!!!!");
            }
        });
    }

    private void fetchReview() {
        Call<ReviewWrapper> reviews = movieApi.reviews(selectedMovie.getValue().getId(), 1);
        reviews.enqueue(new Callback<ReviewWrapper>() {
            @Override
            public void onResponse(Call<ReviewWrapper> call, Response<ReviewWrapper> response) {
//                Log.d(TAG, "onResponse: reviews " + response.toString());
//                Log.d(TAG, "onResponse: reviews " + response.body().toString());
                reviewLiveData.setValue(response.body().getReviews());
            }

            @Override
            public void onFailure(Call<ReviewWrapper> call, Throwable t) {
                Log.d(TAG, "onFailure: Reviews Failed!!!!");
            }
        });
    }

    public LiveData<List<Movie>> getMovies() {
        fetchMovieData();
        return moviesLiveData;
    }

    public void setSelectedMovie(Movie movie) {
//        Log.d(TAG, "set " + movie.toString());
        selectedMovie.setValue(movie);
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }


    public LiveData<List<Review>> getReviews() {
        fetchReview();
        return reviewLiveData;
    }

    public LiveData<List<Video>> getTrailers() {
        fetchTrailer();
        return trailersLiveData;
    }

    public void incrementPageNumber() {
        moviePageNumber++;
        fetchMovieData();
    }

    public void setChangedListType(String type) {
        this.listType = type;
        fetchMovieData();
        this.changedListType.setValue(true);
    }

    public LiveData<Boolean> getChangedListType() {
        return this.changedListType;
    }

    //Room
    public void addFavorite(Movie movie) {
        Log.d(TAG, "addFavorite: " + movie);
        movieRoomDatabase.databaseWriterExecutor.execute(() -> {
            movieDao.addFavorite(movie);
        });
    }

    public void removeFavorite(Movie movie) {
        movieDao.removeFavorite(movie);
    }

    public List<Movie> getAllFavoriteMovies() {
        Log.d(TAG, "get Movielist From Room " + movieDao.getAllFavoriteMovies().toString());

        return movieDao.getAllFavoriteMovies();
    }


}
