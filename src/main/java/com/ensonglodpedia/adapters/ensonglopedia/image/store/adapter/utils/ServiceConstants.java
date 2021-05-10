package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils;

public class ServiceConstants {

    public static final String AUTH_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Authentication succeeded.\","
            + "\"token\": \"%s\","
            + "}";

    public static final String OPERATION_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded.\","
            + "\"token\": \"%s\""
            + "}";

    public static final String IMAGE_POST_FAILURE = "{"
            + "\"success\": false,"
            + "\"message\": \"Operation failure.\","
            + "\"location\": \"\","
            + "\"token\": \"%s\""
            + "}";

    public static final String IMAGE_POST_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded.\","
            + "\"location\": \"REPLACE_ASSET_LOCATION\","
            + "\"token\": \"%s\""
            + "}";
}
