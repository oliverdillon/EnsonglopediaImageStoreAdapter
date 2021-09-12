package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Bean for creating and destroying the orders database inside Apache Derby.
 * 
 * @author Michael Hoffman, Pluralsight
 * 
 */
public class DerbyDatabaseBean {

   private JdbcTemplate jdbcTemplate;

   /**
    * Called by Spring bean initialization. Creates the schema and database
    * table structure for the orders database.
    * 
    * @throws Exception
    */
   public void create() throws Exception {

      try {
         jdbcTemplate.execute("drop table vinyls.images");
         jdbcTemplate.execute("drop table vinyls.songs");
         jdbcTemplate.execute("drop table vinyls.albums");
         jdbcTemplate.execute("drop table vinyls.artists");
         jdbcTemplate.execute("drop table vinyls.vinyls");
         jdbcTemplate.execute("drop schema vinyls restrict");
      } catch (Throwable e) {
      }

      jdbcTemplate.execute("CREATE SCHEMA vinyls");
      jdbcTemplate
              .execute("create table vinyls.vinyls (vinyl_id integer not null, primary key (vinyl_id))");
      jdbcTemplate
              .execute("create table vinyls.artists (artist_id integer not null, artist_name varchar(200) not null, primary key (artist_id))");
      jdbcTemplate
              .execute("create table vinyls.albums (vinyl_id integer not null, album_title varchar(200) not null, artist_id integer not null, release_year integer not null, primary key (vinyl_id))");
      jdbcTemplate
              .execute("alter table vinyls.albums add constraint vinyls_albums_fk_1 foreign key (vinyl_id) references vinyls.vinyls (vinyl_id)");
      jdbcTemplate
              .execute("alter table vinyls.albums add constraint vinyls_albums_fk_2 foreign key (artist_id) references vinyls.artists (artist_id)");
      jdbcTemplate
              .execute("create table vinyls.songs (song_id integer not null, song_title varchar(200) not null, vinyl_id integer not null, primary key (song_id))");
      jdbcTemplate
              .execute("alter table vinyls.songs add constraint vinyls_songs_fk_1 foreign key (vinyl_id) references vinyls.albums (vinyl_id)");
      jdbcTemplate
              .execute("create table vinyls.images (image_id integer not null, image_loc varchar(200) not null, vinyl_id integer not null, primary key (image_id))");
      jdbcTemplate
              .execute("alter table vinyls.images add constraint vinyls_images_fk_1 foreign key (vinyl_id) references vinyls.vinyls (vinyl_id)");
      jdbcTemplate
              .execute("create function vinyls.add_vinyl(vinyl_uuid VARCHAR( 32672 ),artist_uuid VARCHAR( 32672 ),artist VARCHAR( 32672 ), album VARCHAR( 32672 ), release_year VARCHAR( 32672 )) " +
                      "RETURNS INT " +
                      "LANGUAGE JAVA " +
                      "PARAMETER STYLE JAVA " +
                      "NO SQL " +
                      "EXTERNAL NAME 'com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.DerbyFunction.addVinyl'");

   }

   public void modify() throws Exception {
      jdbcTemplate
              .execute("alter table vinyls.images add constraint vinyls_images_fk_1 foreign key (vinyl_id) references vinyls.vinyls (vinyl_id)");
   }

   public void destroy() throws Exception {

      try {
         jdbcTemplate.execute("drop table vinyls.images");
         jdbcTemplate.execute("drop table vinyls.songs");
         jdbcTemplate.execute("drop table vinyls.albums");
         jdbcTemplate.execute("drop table vinyls.artists");
         jdbcTemplate.execute("drop table vinyls.vinyls");
         jdbcTemplate.execute("drop function vinyls.add_vinyl");
         jdbcTemplate.execute("drop schema vinyls restrict");
      } catch (Throwable e) {
         System.out.println(e.getCause());
         // ignore
      }
   }

   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

}
