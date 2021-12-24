insert into vinyls.vinyls(vinyl_id) values ('48e8069c-a121-4f18-871e-8ac8a3c32e8');
insert into vinyls.artists(artist_id, artist_name) values ('a7799923-9e41-4d6b-ad57-a37cbb0b3e90', 'Prince');
insert into vinyls.albums(vinyl_id, album_title, release_year, artist_id) values ('48e8069c-a121-4f18-871e-8ac8a3c32e8','Purple_Rain',1984,'a7799923-9e41-4d6b-ad57-a37cbb0b3e90');

insert into vinyls.images(image_id,image_loc,vinyl_id ) values ('7cd0846a-faca-4cd1-bff3-2d5cb8cfec80','/assets/Purple_Rain.jpg','48e8069c-a121-4f18-871e-8ac8a3c32e8');
insert into vinyls.images(image_id,image_loc,vinyl_id ) values ('df2e7190-2de8-45ba-a7cb-24192433c720','/assets/Purple_Rain_Back.jpg','48e8069c-a121-4f18-871e-8ac8a3c32e8');