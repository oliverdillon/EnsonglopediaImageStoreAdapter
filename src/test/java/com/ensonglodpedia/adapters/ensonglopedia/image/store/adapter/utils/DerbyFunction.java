package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyFunction {

    public static void addVinyl(String vinyl_uuid,String artist_uuid,String artist,String album,
                                String release_year) throws SQLException {

    }

    static Connection getDefaultConnection()
            throws SQLException {
        return DriverManager.getConnection("jdbc:default:connection");
    }

}