package com.example.popularmovies.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.JsonUtils;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.viewModel.MovieViewModel;

import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopularMoviesAdapter.PMadapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private PopularMoviesAdapter mMoviesAdapter;
    private TextView mErrorDisplay;
    private TextView mErrorConnection;
    private ProgressBar mLoadingIndicator;
    private MovieViewModel movieViewModel;
    private LiveData<List<Movie>> allMoviesLiveData;
    private static final String DEBUG_TAG = "NetworkStatusExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(thereIsConnection()){
            setUpRecyclerView();
            loadMovies();
        }else {
            showNetworkConnectionErrorMessage();
        }
    }

    private boolean thereIsConnection() {


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();

    }

    private void setUpRecyclerView(){

        mRecyclerView = findViewById(R.id.rv_movies);
        mMoviesAdapter = new PopularMoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        mRecyclerView.setLayoutManager( new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);


        mErrorDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);


    }

    private void loadMovies(){
        showMoviesDataView();
        URL url = NetworkUtils.buildUrl();
        new FetchMoviesTask().execute(url);
    }
    private void loadMovies(String order){
        showMoviesDataView();
        URL url = NetworkUtils.buildUrl(order);
        new FetchMoviesTask().execute(url);
    }
    private void loadFavourites(){

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.setRepository(getApplication());

        int rows = movieViewModel.countMovies();
        Toast.makeText(this,String.valueOf(rows),
                Toast.LENGTH_LONG).show();

        if(rows<1){
            Toast.makeText(this,"There are no favourites movies added yet.",
                    Toast.LENGTH_LONG).show();

        }else {
            allMoviesLiveData = movieViewModel.getAllMovies();
        Toast.makeText(this,String.valueOf(movieViewModel.getAllMovies().getValue()),
                Toast.LENGTH_LONG).show();
            allMoviesLiveData.observe(this, new Observer<List<Movie>>() {

                @Override
                public void onChanged(List<Movie> movies) {

                    mMoviesAdapter.setMoviesData((ArrayList<Movie>) movies);

                }

            });
        }
}

    @Override
    public void onClick(Movie selectedMovie){
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context,destinationClass);
        intentToStartDetailActivity.putExtra("parcel_data", selectedMovie);
        startActivity(intentToStartDetailActivity);
    }

    private void showMoviesDataView(){
        mErrorDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorDisplay.setVisibility(View.VISIBLE);
    }

    private void showNetworkConnectionErrorMessage(){
        mErrorConnection = findViewById(R.id.tv_error_connection_display);
        mErrorConnection.setVisibility(View.VISIBLE);
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    public class FetchMoviesTask extends AsyncTask<URL, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        @Override
        protected ArrayList<Movie> doInBackground(URL... params) {

            if (params.length == 0){
                return null;
            }

            URL url = params[0];

            try {
                String responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(url);
                JSONArray jsonArray = JsonUtils.getJSONArrayFromHttpUrlResponse(responseFromHttpUrl);
                ArrayList<Movie> moviesList = JsonUtils.getMoviesList(jsonArray);

                return moviesList;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Movie> moviesList){
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if(moviesList!=null){
                showMoviesDataView();
                mMoviesAdapter.setMoviesData(moviesList);

            } else {
                showErrorMessage();
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu, menu);


        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
            if(thereIsConnection()){
                int id = item.getItemId();
                String orderBy ="";

                if(id==R.id.action_favourites){
                    loadFavourites();
                }else {
                    if(id==R.id.action_most_popular){
                        orderBy = "popular";

                    }else if (id==R.id.action_highest_rated){
                        orderBy = "top_rated";

                    }
                    mMoviesAdapter.setMoviesData(null);
                    loadMovies(orderBy);

                }

            }else {
                showNetworkConnectionErrorMessage();
            }

            return super.onOptionsItemSelected(item);


    }


}
