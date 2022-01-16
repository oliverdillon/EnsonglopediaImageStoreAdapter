package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VinylsResponse {

    @JsonProperty("vinyls")
    public List<Vinyl> vinyls;

    public List<Vinyl> getVinyls() {
        return vinyls;
    }

    public void setVinyls(List<Vinyl> vinyls) {
        this.vinyls = vinyls;
    }

    @Override
    public String toString() {
        return "{" +
                "vinyls=" + vinyls.toString() +
                '}';
    }
}
