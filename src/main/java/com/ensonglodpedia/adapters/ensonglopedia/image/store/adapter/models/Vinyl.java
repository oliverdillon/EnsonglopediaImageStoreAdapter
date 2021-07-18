package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Vinyl {

    private String vinyl_id;
    private String artist_name;
    private String album_title;
    private String year;
    private List<String> imgs;

    public String getId() {
        return this.vinyl_id;
    }

    public void setId(String vinyl_id) {
        this.vinyl_id = vinyl_id;
    }

    public String getArtist() {
        return artist_name;
    }

    public void setArtist(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getAlbum() {
        return album_title;
    }

    public void setAlbum(String album_title) {
        this.album_title = album_title;
    }

    public String getDate() {
        return year;
    }

    public void setDate(String year) {
        this.year = year;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + vinyl_id +
                ", artist='" + artist_name + '\'' +
                ", album='" + album_title + '\'' +
                ", date='" + year + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}
