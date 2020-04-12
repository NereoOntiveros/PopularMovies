package com.example.popularmovies.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Query("DELETE FROM movies")
    void deleteAll();

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * from movies where movie_id in (:id)")
    Movie getMovie(int id);

    @Query("SELECT * from movies")
    LiveData<List<Movie>> getAllMovies();

    @Update
    void update(Movie... movie);

    @Query("SELECT COUNT(*) from movies")
    Integer countMovies();
}
