package com.example.popularmovies.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;
import com.example.popularmovies.utils.JsonUtils;
import com.example.popularmovies.utils.NetworkUtils;
import com.example.popularmovies.viewModel.MovieViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailersAdapterOnClickHandler{

    private Movie mMovie;
    private ImageView mPosterDisplay;
    private TextView mTitleDisplay;
    private TextView mReleaseDateDisplay;
    private TextView mVoteAverageDisplay;
    private TextView mSynopsisDisplay;
    private RecyclerView mReviewsRecyclerView;
    private RecyclerView mTrailersRecyclerView ;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private MovieViewModel mMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        mPosterDisplay = findViewById(R.id.iv_display_poster);
        mTitleDisplay = findViewById(R.id.tv_display_title);
        mReleaseDateDisplay = findViewById(R.id.tv_display_release);
        mVoteAverageDisplay = findViewById(R.id.tv_display_average);
        mSynopsisDisplay = findViewById(R.id.tv_display_synopsis);
        mSynopsisDisplay.setMovementMethod(new ScrollingMovementMethod());


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity!=null){

            if(intentThatStartedThisActivity.hasExtra("parcel_data")){

                mMovie = getIntent().getParcelableExtra("parcel_data");
                String mMoviePoster = mMovie.getMovie_poster();

                String pathToPoster = "https://image.tmdb.org/t/p/w185"+mMoviePoster;

                Picasso.get().load(pathToPoster)
                        .fit()
                        .into(mPosterDisplay);

                mTitleDisplay.setText(mMovie.getTitle());


                String[] date = mMovie.getRelease_date().split("-");
                mReleaseDateDisplay.setText(date[0]);

                mVoteAverageDisplay.setText(mMovie.getVote_avg().toString()+"/10");
                mSynopsisDisplay.setText(mMovie.getSynopsis());



                mReviewsRecyclerView=findViewById(R.id.rv_reviews);
                mTrailersRecyclerView =findViewById(R.id.rv_trailers);


                trailersAdapter = new TrailersAdapter(this);
                reviewsAdapter = new ReviewsAdapter();

                mTrailersRecyclerView.setAdapter(trailersAdapter);
                mReviewsRecyclerView.setAdapter(reviewsAdapter);

                mTrailersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

                loadTrailers(mMovie.getMovie_id());
                loadReviews(mMovie.getMovie_id());

                mMovieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);


                Log.d("Prueba viewmodel:",mMovieViewModel.toString());
            }

        }
    }

    private void loadTrailers(int id){

        URL url = NetworkUtils.buildTrailerUrl(id);
        new FetchTrailersTask().execute(url);
    }

    private void loadReviews(int id){
        URL url = NetworkUtils.buildReviewUrl(id);
        new FetchReviewsTask().execute(url);
    }

    public class FetchTrailersTask extends AsyncTask<URL, Void , ArrayList<Trailer>>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }
        @Override
        protected ArrayList<Trailer> doInBackground(URL... params) {
            if (params.length == 0){
                return null;
            }

            URL url = params[0];

            try {
                String responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(url);
                JSONArray jsonArray = JsonUtils.getJSONArrayFromHttpUrlResponse(responseFromHttpUrl);
                ArrayList<Trailer> trailersList = JsonUtils.getTrailersList(jsonArray);

                return trailersList;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(ArrayList<Trailer> trailersList){

                trailersAdapter.setTrailersData(trailersList);

        }
    }
    public class FetchReviewsTask extends AsyncTask<URL, Void , ArrayList<Review>>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }
        @Override
        protected ArrayList<Review> doInBackground(URL... params) {
            if (params.length == 0){
                return null;
            }

            URL url = params[0];

            try {
                String responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(url);
                JSONArray jsonArray = JsonUtils.getJSONArrayFromHttpUrlResponse(responseFromHttpUrl);
                ArrayList<Review> reviewsList = JsonUtils.getReviewsList(jsonArray);

                return reviewsList;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(ArrayList<Review> reviewsList){

            reviewsAdapter.setReviewsData(reviewsList);

        }
    }
    @Override
    public void onClick(Trailer selectedTrailer){

        Context context = this;
        Intent intent = new  Intent(Intent.ACTION_VIEW);

        intent.setPackage("com.google.android.youtube");
        intent.setData(Uri.parse("https://www.youtube.com/watch?v="+selectedTrailer.getKey()));
        intent.getPackage();
        startActivity(intent);
    }

    public void toggleFavourite(View view){
        Log.d("pruebaaaaa",mMovie.getTitle());


        if(mMovieViewModel.getMovie(mMovie)!=null){
            mMovieViewModel.deleteMovie(mMovie);

            Toast.makeText(this,"Movie deleted from your favourites",
                    Toast.LENGTH_LONG).show();

        }else {
            mMovieViewModel.insert(mMovie);
            Toast.makeText(this,"Movie added to your favourites",
                    Toast.LENGTH_LONG).show();
        }



    }







}
