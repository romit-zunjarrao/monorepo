package com.rz.movieguide.repository.remote;

import com.rz.movieguide.model.MovieWrapper;
import com.rz.movieguide.model.Review;
import com.rz.movieguide.model.ReviewWrapper;
import com.rz.movieguide.model.Video;
import com.rz.movieguide.model.VideoWrapper;

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
    Call<ReviewWrapper> reviews(@Path("movie_id")String id, @Query("page")int page);

    @GET("3/movie/{movie_id}/videos?language=en-US")
    Call<VideoWrapper> trailers(@Path("movie_id")String id, @Query("page")int page);
}
