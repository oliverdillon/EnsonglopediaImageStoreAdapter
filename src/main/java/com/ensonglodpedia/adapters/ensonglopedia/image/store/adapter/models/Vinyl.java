package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Vinyl {

    @JsonProperty("vinyl_id")
    @JsonAlias("id")
    private String id;

    @JsonProperty("artist_name")
    @JsonAlias("artist")
    private String artist;

    @JsonProperty("album_title")
    @JsonAlias("album")
    private String album;

    @JsonProperty("year")
    @JsonAlias("date")
    private String date;

    private List<String> imgs;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
                "id=" + id +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", date='" + date + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}
