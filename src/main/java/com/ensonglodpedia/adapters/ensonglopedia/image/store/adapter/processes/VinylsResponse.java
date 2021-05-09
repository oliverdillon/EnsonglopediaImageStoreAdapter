package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VinylsResponse {

    @JsonProperty("data")
    public List<Vinyl> data;

    public List<Vinyl> getData() {
        return data;
    }

    public void setData(List<Vinyl> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "data=" + data.toString() +
                '}';
    }
}
