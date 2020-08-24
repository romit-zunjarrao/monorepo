package com.rz.movieguide.repository.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rz.movieguide.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavorite(Movie movie);

    @Delete
    void removeFavorite(Movie movie);

    @Query("SELECT * from movie")
    List<Movie> getAllFavoriteMovies();
}
