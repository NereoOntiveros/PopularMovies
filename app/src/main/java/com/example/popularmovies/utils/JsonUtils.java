package com.example.popularmovies.utils;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class JsonUtils {

    public static JSONArray getJSONArrayFromHttpUrlResponse (String responseFromHttpUrl){
        JSONArray responseArray = new JSONArray();
        try{
            JSONObject data = new JSONObject(responseFromHttpUrl);
            responseArray = data.getJSONArray("results");


        }catch (JSONException e){
            e.printStackTrace();
        }
        return responseArray;
    }

    public static ArrayList<Movie> getMoviesList(JSONArray jsonArray){

        ArrayList<Movie> moviesList = new ArrayList<Movie>();

        try {//loops the JSONArray to parse every JSONObject into a Movie object
            for (int i=0; i<jsonArray.length(); i ++){
                JSONObject jsonMovie = jsonArray.getJSONObject(i);
                Movie movie = new Movie();

                //Builds the Movie object
                movie.setMovie_id(jsonMovie.getInt("id"));
                movie.setMovie_poster(jsonMovie.getString("poster_path"));
                movie.setRelease_date(jsonMovie.getString("release_date"));
                movie.setSynopsis(jsonMovie.getString("overview"));
                movie.setTitle(jsonMovie.getString("title"));
                movie.setVote_avg(jsonMovie.getDouble("vote_average"));

                //Then add it to the list of movies
                moviesList.add(movie);

            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }


        return moviesList;
    }

    public static ArrayList<Trailer> getTrailersList(JSONArray jsonArray){
        ArrayList<Trailer> trailersList = new ArrayList<Trailer>();

        try {//loops the JSONArray to parse every JSONObject into a Trailer object
            for (int i=0; i<jsonArray.length(); i ++){
                JSONObject jsonTrailer = jsonArray.getJSONObject(i);
                Trailer trailer = new Trailer();

                //Builds the Trailer object
                trailer.setId(jsonTrailer.getString("id"));
                trailer.setIso_639_1(jsonTrailer.getString("iso_639_1"));
                trailer.setIso_3166_1(jsonTrailer.getString("iso_3166_1"));
                trailer.setName(jsonTrailer.getString("name"));
                trailer.setKey(jsonTrailer.getString("key"));
                trailer.setSite(jsonTrailer.getString("site"));
                trailer.setSize(jsonTrailer.getInt("size"));
                trailer.setType(jsonTrailer.getString("type"));


                //Then add it to the list of trailers
                trailersList.add(trailer);

            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return trailersList;
    }
    public static ArrayList<Review> getReviewsList(JSONArray jsonArray){
        ArrayList<Review> reviewsList = new ArrayList<Review>();

        try {//loops the JSONArray to parse every JSONObject into a Review object
            for (int i=0; i<jsonArray.length(); i ++){
                JSONObject jsonReview = jsonArray.getJSONObject(i);
                Review review = new Review();

                //Builds the Review object

                review.setId(jsonReview.getString("id"));
                review.setAuthor(jsonReview.getString("author"));
                review.setContent(jsonReview.getString("content"));
                review.setUrl(jsonReview.getString("url"));

                //Then add it to the list of reviews
                reviewsList.add(review);

            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }


        return reviewsList;
    }





}
