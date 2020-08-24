package com.rz.movieguide.repository.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rz.movieguide.model.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public static MovieRoomDatabase movieRoomDatabaseInstance;

    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(4);

    public static MovieRoomDatabase getMovieRoomDatabaseInstance(Context context) {
        if (movieRoomDatabaseInstance == null) {
            synchronized (MovieRoomDatabase.class) {
                if (movieRoomDatabaseInstance == null) {
                    movieRoomDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            .build();
                }
            }
        }
        return movieRoomDatabaseInstance;
    }


}
