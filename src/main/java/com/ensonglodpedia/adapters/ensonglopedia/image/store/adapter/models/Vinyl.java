package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Vinyl {

    private String vinyl_id;
    private String artist_name;
    private String album_title;
    private String release_year;
    private List<String> imgs;

    @JsonProperty("VINYL_ID")
    public String getVinyl_id() {
        return vinyl_id;
    }

    @JsonProperty("VINYL_ID")
    public void setVinyl_id(String vinyl_id) {
        this.vinyl_id = vinyl_id;
    }

    @JsonProperty("ARTIST_NAME")
    public String getArtist_name() {
        return artist_name;
    }

    @JsonProperty("ARTIST_NAME")
    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    @JsonProperty("ALBUM_TITLE")
    public String getAlbum_title() {
        return album_title;
    }

    @JsonProperty("ALBUM_TITLE")
    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    @JsonProperty("RELEASE_YEAR")
    public String getRelease_year() {
        return release_year;
    }

    @JsonProperty("RELEASE_YEAR")
    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    @JsonProperty("IMGS")
    public List<String> getImgs() {
        return imgs;
    }

    @JsonProperty("IMGS")
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

