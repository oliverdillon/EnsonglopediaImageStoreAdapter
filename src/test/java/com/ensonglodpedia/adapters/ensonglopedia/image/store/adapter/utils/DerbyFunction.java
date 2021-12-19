package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DerbyFunction {

    public static void addVinyl(String vinyl_uuid,String artist_uuid,String artist_name,String album_title,
                                int release_year) throws SQLException {
            Connection connection = getDefaultConnection();
            connection.setAutoCommit(false);
            String vinyls_sql = "insert into vinyls.vinyls(vinyl_id) values (?)";
            PreparedStatement vinyls_statement = connection.prepareStatement(vinyls_sql);
            vinyls_statement.setString(1, vinyl_uuid);
            vinyls_statement.executeUpdate();
            connection.commit();
            vinyls_statement.close();

            String artists_sql = "insert into vinyls.artists(artist_id, artist_name) values (?, ?)";
            PreparedStatement artists_statement = connection.prepareStatement(artists_sql);
            artists_statement.setString(1, artist_uuid);
            artists_statement.setString(2, artist_name);
            artists_statement.executeUpdate();
            connection.commit();
            artists_statement.close();

            String albums_sql = "insert into vinyls.albums(vinyl_id, album_title, release_year, artist_id) values (?,?,?,?)";
            PreparedStatement albums_statement = connection.prepareStatement(albums_sql);
            albums_statement.setString(1, vinyl_uuid);
            albums_statement.setString(2, album_title);
            albums_statement.setInt(3, release_year);
            albums_statement.setString(4, artist_uuid);
            albums_statement.executeUpdate();
            connection.commit();
            albums_statement.close();

            connection.close();
    }

    static Connection getDefaultConnection()
            throws SQLException {
        return DriverManager.getConnection("jdbc:default:connection");
    }

}