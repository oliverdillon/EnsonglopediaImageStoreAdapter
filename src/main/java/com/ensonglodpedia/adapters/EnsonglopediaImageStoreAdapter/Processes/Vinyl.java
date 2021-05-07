package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes;

import java.time.Instant;

public class Vinyl {
    private String id;
    private String artist;
    private String album;
    private String date;
//    private List<String> imgs;

    public String getId() {
        return id;
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

//    public List<String> getImgs() {
//        return imgs;
//    }
//
//    public void setImgs(List<String> imgs) {
//        this.imgs = imgs;
//    }
}
