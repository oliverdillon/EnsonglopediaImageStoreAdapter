package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Vinyl {

    private String vinyl_id;
    private String artist_name;
    private String album_title;
    private String release_year;
    private List<String> imgs;

    @JsonProperty("vinyl_id")
    public String getVinyl_id() {
        return vinyl_id;
    }

    @JsonProperty("vinyl_id")
    public void setVinyl_id(String vinyl_id) {
        this.vinyl_id = vinyl_id;
    }

    @JsonProperty("artist_name")
    public String getArtist_name() {
        return artist_name;
    }

    @JsonProperty("artist_name")
    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    @JsonProperty("album_title")
    public String getAlbum_title() {
        return album_title;
    }

    @JsonProperty("album_title")
    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    @JsonProperty("release_year")
    public String getRelease_year() {
        return release_year;
    }

    @JsonProperty("release_year")
    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    @JsonProperty("imgs")
    public List<String> getImgs() {
        return imgs;
    }

    @JsonProperty("imgs")
    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "{" +
                "vinyl_id='" + vinyl_id + '\'' +
                ", artist_name='" + artist_name + '\'' +
                ", album_title='" + album_title + '\'' +
                ", year='" + release_year + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}

