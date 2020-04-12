package com.example.popularmovies.viewModel;


import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.MovieRepository;

import java.util.List;

/*MovieViewModel provides the interface between the UI and the data layer of the app,
* represented by the Repository*/

public class MovieViewModel extends ViewModel {
    private MovieRepository mRepository;
    private LiveData<List<Movie>> mAllMovies;
    private MutableLiveData<Integer> numberOfrows;
    private MutableLiveData<Movie> movieMLD;

    public MovieViewModel(){

    }

    public void setRepository(Application application){
        mRepository = new MovieRepository(application);
        mAllMovies = mRepository.getAllMovies();
        numberOfrows=mRepository.getNumberOfRows();


    }

    public LiveData<List<Movie>> getAllMovies(){return mAllMovies;}

    public void insert(Movie movie){mRepository.insert(movie);}
    public void deleteAll(){mRepository.deleteAll();}
    public void deleteMovie(Movie movie){mRepository.deleteMovie(movie);}
    public void update(Movie movie){mRepository.update(movie);}
    public MutableLiveData<Movie> getMovie(Integer id){

        movieMLD=mRepository.getMovie(id);

        return movieMLD;
    }
    public MutableLiveData<Integer> countMovies(){ mRepository.countMovies(); return numberOfrows;}


}
