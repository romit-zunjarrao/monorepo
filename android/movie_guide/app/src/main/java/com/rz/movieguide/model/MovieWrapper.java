package com.rz.movieguide.model;

import com.squareup.moshi.Json;

import java.util.List;

public class MovieWrapper {

    @Json(name = "results")
    List<Movie> movies;


    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MovieWrapper{" +
                "movies=" + movies +
                '}';
    }
}
