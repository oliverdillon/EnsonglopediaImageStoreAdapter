package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.database;

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
         jdbcTemplate.execute("drop table vinyls.vinyls");
         jdbcTemplate.execute("drop table vinyls.artists");
         jdbcTemplate.execute("drop table vinyls.albums");
         jdbcTemplate.execute("drop table vinyls.songs");
         jdbcTemplate.execute("drop table vinyls.images");
         jdbcTemplate.execute("drop schema vinyls");
      } catch (Throwable e) {
      }

      jdbcTemplate.execute("CREATE SCHEMA vinyls");
      jdbcTemplate
              .execute("create table vinyls.vinyls (id integer not null, primary key (id))");
      jdbcTemplate
              .execute("create table vinyls.artists (id integer not null, artist_name varchar(200) not null, primary key (id))");
      jdbcTemplate
              .execute("create table vinyls.albums (id integer not null, album_title varchar(200) not null, artist_id integer not null, release_year integer not null, primary key (id))");
      jdbcTemplate
              .execute("alter table vinyls.albums add constraint vinyls_albums_fk_1 foreign key (id) references vinyls.vinyls (id)");
      jdbcTemplate
              .execute("alter table vinyls.albums add constraint vinyls_albums_fk_2 foreign key (artist_id) references vinyls.artists (id)");
      jdbcTemplate
              .execute("create table vinyls.songs (id integer not null, song_title varchar(200) not null, vinyl_id integer not null, primary key (id))");
      jdbcTemplate
              .execute("alter table vinyls.songs add constraint vinyls_songs_fk_1 foreign key (vinyl_id) references vinyls.albums (id)");
      jdbcTemplate
              .execute("create table vinyls.images (id integer not null, image_loc varchar(200) not null, vinyl_id integer not null, primary key (id))");
      jdbcTemplate
              .execute("alter table vinyls.images add constraint vinyls_images_fk_1 foreign key (vinyl_id) references vinyls.vinyls (id)");
   }

   public void destroy() throws Exception {

      try {
         jdbcTemplate.execute("drop table vinyls.vinyls");
         jdbcTemplate.execute("drop table vinyls.artists");
         jdbcTemplate.execute("drop table vinyls.albums");
         jdbcTemplate.execute("drop table vinyls.songs");
         jdbcTemplate.execute("drop table vinyls.images");
         jdbcTemplate.execute("drop schema vinyls");
      } catch (Throwable e) {
         // ignore
      }
   }

   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

}
