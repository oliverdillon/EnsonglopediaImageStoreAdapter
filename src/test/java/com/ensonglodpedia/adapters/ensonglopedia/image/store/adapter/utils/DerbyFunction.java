package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyFunction {

    public static int addVinyl(String vinyl_uuid,String artist_uuid,String artist,String album,
                                int release_year) throws SQLException {
        return 0;

    }

    static Connection getDefaultConnection()
            throws SQLException {
        return DriverManager.getConnection("jdbc:default:connection");
    }

}