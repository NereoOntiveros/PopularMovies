package com.example.popularmovies.model;

public class Review {

    private String id;
    private String author;
    private String content;
    private String url;

    public Review(){}
    public Review(String id, String author, String content, String url){
        this.id = id;
        this.author = author;
        this.content=content;
        this.url = url;
    }

    public void setId(String id){
        this.id = id;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setContent(String content){
        this.content=content;
    }
    public void setUrl(String url){
        this.url=url;
    }


    public String getId(){
        return this.id;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getContent(){
        return this.content;
    }
    public String getUrl(){
        return this.url;
    }
}
