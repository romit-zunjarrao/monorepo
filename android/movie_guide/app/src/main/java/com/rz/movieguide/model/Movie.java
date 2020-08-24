package com.rz.movieguide.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

@Entity(tableName = "movie")
public class Movie {

    public static final String BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342";
    private static final String BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780";

    private static final String TAG = "Movie";

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo
    private String overview;

    @ColumnInfo(name = "release_date")
    @Json(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "backdrop_path")
    @Json(name = "backdrop_path")
    private String backdropPath;

    @ColumnInfo
    private String title;

    @ColumnInfo(name = "vote_average")
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
        return BASE_BACKDROP_PATH + backdropPath;
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
