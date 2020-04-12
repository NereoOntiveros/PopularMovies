package com.example.popularmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String SCHEME= "https";
    private static final String AUTHORITY = "api.themoviedb.org";
    private static final String PATH_1="3";
    private static final String PATH_2="discover";
    private static final String PATH_3="movie";

    /*=======PLEASE INSERT API KEY HERE: ============  */
    private static final String API_KEY="85ecdecc23fab975c727432b54b0a5e1";




    public static URL buildUrl(){
        Uri.Builder uri = new Uri.Builder();
        URL url = null;
        uri.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(PATH_1)
                .appendPath(PATH_2)
                .appendPath(PATH_3)
                .appendQueryParameter("api_key",API_KEY);


        try {
            url = new URL(uri.toString());

        }catch (MalformedURLException e){
            e.printStackTrace();
        }


        return url;
    }

    public static URL buildUrl(String sortedBy){

        Uri.Builder uri = new Uri.Builder();
        URL url = null;
        uri.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(PATH_1)
                .appendPath(PATH_3)
                .appendPath(sortedBy)
                .appendQueryParameter("api_key",API_KEY);
        try {
            url = new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }


    public static URL buildReviewUrl(int movieId){
        Uri.Builder uri = new Uri.Builder();
        URL url = null;

        uri.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(PATH_1)
                .appendPath(PATH_3)
                .appendPath(Integer.toString(movieId))
                .appendPath("reviews")
                .appendQueryParameter("api_key",API_KEY);
        try {
            url = new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
    public static URL buildTrailerUrl(int movieId){
        Uri.Builder uri = new Uri.Builder();
        URL url = null;

        uri.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(PATH_1)
                .appendPath(PATH_3)
                .appendPath(Integer.toString(movieId))
                .appendPath("videos")
                .appendQueryParameter("api_key",API_KEY);
        try {
            url = new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }



    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }



}
