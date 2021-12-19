CREATE SCHEMA vinyls;
create table vinyls.vinyls (vinyl_id VARCHAR( 200 ) not null,
							primary key (vinyl_id));
create table vinyls.artists (artist_id VARCHAR( 200 ) not null,
							 artist_name varchar(200) not null,
							 primary key (artist_id));
create table vinyls.albums (vinyl_id VARCHAR( 200 ) not null,
							album_title varchar(200) not null,
							artist_id VARCHAR( 200 ) not null,
							release_year VARCHAR( 200 ) not null,
							primary key (vinyl_id),
						    foreign key (vinyl_id)
        					references vinyls.vinyls (vinyl_id) MATCH SIMPLE,
						    foreign key (artist_id)
        					references vinyls.artists (artist_id) MATCH SIMPLE);
create table vinyls.songs (song_id VARCHAR( 200 ) not null,
						   song_title varchar(200) not null,
						   vinyl_id VARCHAR( 200 ) not null,
						   primary key (song_id),
						   foreign key (vinyl_id)
						   references vinyls.albums (vinyl_id) MATCH SIMPLE);
create table vinyls.images (image_id VARCHAR( 200 ) not null,
							image_loc varchar(200) not null,
							vinyl_id VARCHAR( 200 ) not null,
							primary key (image_id),
						   	foreign key (vinyl_id)
						    references vinyls.vinyls (vinyl_id) MATCH SIMPLE);
CREATE OR REPLACE PROCEDURE vinyls.add_vinyl(
	vinyl_uuid uuid,
	artist_uuid uuid,
	artist_name character varying,
	album_title character varying,
	release_year integer)
LANGUAGE 'sql'
AS $BODY$
insert into vinyls.vinyls(vinyl_id) values (vinyl_uuid);
insert into vinyls.artists(artist_id, artist_name) values (artist_uuid, artist_name);
insert into vinyls.albums(vinyl_id, album_title, release_year, artist_id) values (vinyl_uuid,album_title,release_year,artist_uuid);
$BODY$;