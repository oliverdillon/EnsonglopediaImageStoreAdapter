package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImagePostResponse {
    @JsonProperty("success")
    public boolean success;

    @JsonProperty("message")
    public String message;

    @JsonProperty("location")
    public String location;

    @JsonProperty("token")
    public String token;

    public ImagePostResponse(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }


    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ImagePostResponse{" +
                "success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", location='" + location + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

}
