package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.local;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VinylsLegacyResponse {

    @JsonProperty("data")
    public List<VinylLegacy> data;

    public List<VinylLegacy> getData() {
        return data;
    }

    public void setData(List<VinylLegacy> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "data=" + data.toString() +
                '}';
    }
}
