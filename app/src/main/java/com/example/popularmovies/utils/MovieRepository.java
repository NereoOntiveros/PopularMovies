package com.example.popularmovies.utils;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieDao;
import com.example.popularmovies.model.MovieRoomDatabase;

import java.util.List;

public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

    public MovieRepository(Application application){
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovies();
    }

    public LiveData<List<Movie>> getAllMovies(){
        return mAllMovies;
    }

    public void  insert(Movie movie){new insertAsyncTask(mMovieDao).execute(movie);}
    public void update(Movie movie){new updateMovieAsyncTask(mMovieDao).execute(movie);}
    public void deleteAll(){new deleteAllMoviesAsyncTask(mMovieDao).execute();}
    public Movie getMovie(Movie movie){new getMovieAsyncTask(mMovieDao).execute(movie);
        return movie;
    }
    /*Must run off main thread*/
    public void deleteMovie(Movie movie){new deleteMovieAsyncTask(mMovieDao).execute(movie);}

    //**********Static inner classes to run database interactions in the background:*******

    /*Inserts a movie into the database*/

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void>{
        private MovieDao mAsynctaskDao;

        insertAsyncTask(MovieDao dao){mAsynctaskDao=dao;}
        @Override
        protected Void doInBackground(final Movie... params){
            mAsynctaskDao.insert(params[0]);
            return null;
        }
    }

    /*Deletes all movies from the database (does not delete the table)*/

    private static class deleteAllMoviesAsyncTask extends AsyncTask<Void,Void,Void>{
        private MovieDao mAsyncTaskDao;

        deleteAllMoviesAsyncTask(MovieDao dao){mAsyncTaskDao=dao;}

        @Override
        protected Void doInBackground(Void... voids){
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    /*Deletes a single movie from the database*/

    private static class deleteMovieAsyncTask extends AsyncTask<Movie,Void,Void>{
        private MovieDao mAsyncTaskDao;

        deleteMovieAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.deleteMovie(params[0]);
            return null;
        }
    }

    /*Updates a movie in the database*/
    private static class updateMovieAsyncTask extends AsyncTask<Movie,Void,Void>{
        private MovieDao mAsyncTaskDao;

        updateMovieAsyncTask(MovieDao dao){mAsyncTaskDao=dao;}

        @Override
        protected Void doInBackground(final Movie... params){
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    /*Gets a movie from the database*/

    private static class getMovieAsyncTask extends AsyncTask<Movie, Void, Movie> {

        private MovieDao mAsyncTaskDao;

        getMovieAsyncTask(MovieDao dao){mAsyncTaskDao=dao;}


        @Override
        protected Movie doInBackground(Movie... movies) {

            return mAsyncTaskDao.getMovie(movies[0].getMovie_id());

        }
    }

}
