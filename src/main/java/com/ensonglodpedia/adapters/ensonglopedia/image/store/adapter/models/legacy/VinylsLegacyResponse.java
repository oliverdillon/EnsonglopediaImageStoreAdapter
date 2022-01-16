package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VinylsLegacyResponse {

    @JsonProperty("vinyls")
    public List<VinylLegacy> vinyls;

    public List<VinylLegacy> getVinyls() {
        return vinyls;
    }

    public void setVinyls(List<VinylLegacy> vinyls) {
        this.vinyls = vinyls;
    }

    @Override
    public String toString() {
        return "{" +
                "vinyls=" + vinyls.toString() +
                '}';
    }
}
