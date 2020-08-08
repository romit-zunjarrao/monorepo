package com.rz.movieguide.repository.remote;

import com.rz.movieguide.model.MovieWrapper;
import com.rz.movieguide.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("3/movie/popular?language=en-US")
    Call<MovieWrapper> popularMovies(@Query("page")int page);

    @GET("3/movie/now_playing?language=en-US")
    Call<MovieWrapper> nowPlaying(@Query("page")int page);

    @GET("3/movie/top_rated?language=en-US")
    Call<MovieWrapper> topRatedMovies(@Query("page")int page);

    @GET("3/movie/{movie_id}/reviews?language=en-US")
    Call<List<Review>> reviews(@Path("movie_id")int id,@Query("page")int page);
}
