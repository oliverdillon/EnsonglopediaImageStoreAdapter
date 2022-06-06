package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = BytesDeserializer.class)
public class ImagePostRequest {

    @JsonProperty("vinyl_uuid")
    public String vinyl_uuid;

    @JsonProperty("filename")
    public String filename;

    @JsonProperty("file")
    public byte[] file;

    public String getVinyl_uuid() {
        return vinyl_uuid;
    }

    public void setVinyl_uuid(String vinyl_uuid) {
        this.vinyl_uuid = vinyl_uuid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ImagePostRequest(String vinyl_uuid, String filename, byte[] file) {
        this.vinyl_uuid = vinyl_uuid;
        this.filename = filename;
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file)  {
        this.file = file;
    }

}
