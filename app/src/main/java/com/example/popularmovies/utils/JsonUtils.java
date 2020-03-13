package com.example.popularmovies.utils;

import com.example.popularmovies.model.Movie;

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
            for (int i=0; i<=jsonArray.length(); i ++){
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





}
