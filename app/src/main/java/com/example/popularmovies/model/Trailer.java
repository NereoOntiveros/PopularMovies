package com.example.popularmovies.model;

public class Trailer {

    private String id;
    private String  iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public Trailer(){}

    public Trailer(String id, String iso_639_1, String iso_3166_1, String key, String name,String site, int size,String type){
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1=iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public void setId(String id){
        this.id=id;
    }
    public void setIso_639_1(String iso_639_1){
        this.iso_639_1 = iso_639_1;
    }
    public void setIso_3166_1(String iso_3166_1){
        this.iso_3166_1 = iso_3166_1;
    }
    public void setKey(String key){
        this.key = key;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSite(String site){
        this.site = site;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getId (){
        return this.id;
    }
    public String getIso_639_1(){
        return this.iso_639_1;
    }
    public String getIso_3166_1(){
        return this.iso_3166_1;
    }
    public String getKey(){
        return this.key;
    }
    public String getName(){
        return this.name;
    }
    public String getSite(){
        return this.site;
    }
    public int getSize(){
        return this.size;
    }
    public String getType(){
        return this.type;
    }

}
