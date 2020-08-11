package com.rz.movieguide.model;

import com.squareup.moshi.Json;

public class Movie {

    public static final String BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342";
    private static final String BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780";

    private static final String TAG = "Movie";
    private String id;
    private String overview;
    @Json(name = "release_date")
    private String releaseDate;
    @Json(name = "poster_path")
    private String posterPath;
    @Json(name = "backdrop_path")
    private String backdropPath;
    private String title;
    @Json(name = "vote_average")
    private double voteAverage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return BASE_POSTER_PATH + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return BASE_BACKDROP_PATH+backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", title='" + title + '\'' +
                ", voteAverage=" + voteAverage +
                '}';
    }
}
