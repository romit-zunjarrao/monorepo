package com.rz.movieguide.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rz.movieguide.model.Movie;
import com.rz.movieguide.model.Review;
import com.rz.movieguide.model.Video;
import com.rz.movieguide.repository.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;


    public MovieListViewModel(){
        movieRepository = MovieRepository.getMovieRepository();
    }

    public LiveData<List<Movie>> getMovieList(){
        return movieRepository.getMovies();
    }

    public void setSelectedMovie(Movie movie){
        movieRepository.setSelectedMovie(movie);
    }

    public LiveData<Movie> getSelectedMovie(){
      return movieRepository.getSelectedMovie();
    }

    public LiveData<List<Video>> getTrailers(){ return movieRepository.getTrailers();}

    public LiveData<List<Review>> getReviews() { return movieRepository.getReviews();}


}
