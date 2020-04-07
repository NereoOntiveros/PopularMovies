package com.example.popularmovies.viewModel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.MovieRepository;

import java.util.List;

/*MovieViewModel provides the interface between the UI and the data layer of the app,
* represented by the Repository*/
//public class MovieViewModel extends ViewModel {
public class MovieViewModel extends AndroidViewModel{
    private MovieRepository mRepository;
    private LiveData<List<Movie>> mAllMovies;

    //public MovieViewModel(){}

        public MovieViewModel(Application application){
        super(application);
        mRepository = new MovieRepository(application);
        mAllMovies = mRepository.getAllMovies();


    }

    public LiveData<List<Movie>> getAllMovies(){return mAllMovies;}

    public void insert(Movie movie){mRepository.insert(movie);}
    public void deleteAll(){mRepository.deleteAll();}
    public void deleteMovie(Movie movie){mRepository.deleteMovie(movie);}
    public void update(Movie movie){mRepository.update(movie);}
    public Movie getMovie(Movie movie){mRepository.getMovie(movie);
        return movie;
    }
}
