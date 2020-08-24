package com.rz.movieguide.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rz.movieguide.model.Movie;
import com.rz.movieguide.model.Review;
import com.rz.movieguide.model.Video;
import com.rz.movieguide.repository.MovieRepository;

import java.util.List;

public class MovieListViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;


    public MovieListViewModel(Application application) {
        super(application);
        movieRepository = MovieRepository.getMovieRepository(application);
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieRepository.getMovies();
    }

    public void setSelectedMovie(Movie movie) {
        movieRepository.setSelectedMovie(movie);
    }

    public LiveData<Movie> getSelectedMovie() {
        return movieRepository.getSelectedMovie();
    }

    public LiveData<List<Video>> getTrailers() {
        return movieRepository.getTrailers();
    }

    public LiveData<List<Review>> getReviews() {
        return movieRepository.getReviews();
    }

    public void incrementPageNumber() {
        movieRepository.incrementPageNumber();
    }

    public void setListType(String type) {
        movieRepository.setChangedListType(type);
    }

    public LiveData<Boolean> getListType() {
        return movieRepository.getChangedListType();
    }

    //Room
    public void addFavorite() {
        movieRepository.addFavorite(this.getSelectedMovie().getValue());
    }

    public void removeFavorite(Movie movie) {
        movieRepository.removeFavorite(this.getSelectedMovie().getValue());
    }
}
