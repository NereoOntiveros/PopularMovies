package com.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String title;
    private String release_date;
    private String movie_poster;
    private String synopsis;
    private Double vote_avg;
    private Integer movie_id;

    public Movie(){}
    public Movie(Parcel in){
        title = in.readString();
        release_date=in.readString();
        movie_poster = in.readString();
        synopsis = in.readString();
        vote_avg = in.readDouble();
        movie_id = in.readInt();
    }

    public Movie(String title, String release_date, String movie_poster, String synopsis
    , Double vote_avg, Integer movie_id){
        this.title = title;
        this.release_date = release_date;
        this.movie_poster = movie_poster;
        this.synopsis = synopsis;
        this.vote_avg = vote_avg;
        this.movie_id = movie_id;

    }

    public void setTitle(String title){ this.title = title;}
    public void setRelease_date(String release_date){
            this.release_date= release_date;
    }
    public void setMovie_poster(String movie_poster){this.movie_poster=movie_poster;}
    public void setSynopsis(String synopsis){this.synopsis = synopsis;}
    public void setVote_avg(Double vote_avg){this.vote_avg=vote_avg;}
    public void setMovie_id(Integer movie_id){this.movie_id=movie_id;}

    public String getTitle(){return title;}
    public String getRelease_date(){
        return release_date ;
    }
    public String getMovie_poster(){return movie_poster;}
    public String getSynopsis(){return synopsis;}
    public Double getVote_avg(){return vote_avg;}
    public Integer getMovie_id(){return movie_id;}

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(movie_poster);
        dest.writeString(synopsis);
        dest.writeDouble(vote_avg);
        dest.writeInt(movie_id);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel in) {

                return new Movie(in);

        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
