package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private ImageView mPosterDisplay;
    private TextView mTitleDisplay;
    private TextView mReleaseDateDisplay;
    private TextView mVoteAverageDisplay;
    private TextView mSynopsisDisplay;

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

            }

        }
    }

}
