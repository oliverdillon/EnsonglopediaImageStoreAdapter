package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VinylLegacy  {
    @JsonProperty("vinyl_id")
    private String vinyl_id;

    @JsonProperty("artist_name")
    private String artist_name;

    @JsonProperty("album_title")
    private String album_title;

    @JsonProperty("release_year")
    private String release_year;

    @JsonProperty("imgs")
    private List<String> imgs;

    public String getVinyl_id() {
        return vinyl_id;
    }

    public void setVinyl_id(String vinyl_id) {
        this.vinyl_id = vinyl_id;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public String getRelease_year() {
        return release_year;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
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
                "vinyl_id='" + vinyl_id + '\'' +
                ", artist_name='" + artist_name + '\'' +
                ", album_title='" + album_title + '\'' +
                ", year='" + release_year + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}

