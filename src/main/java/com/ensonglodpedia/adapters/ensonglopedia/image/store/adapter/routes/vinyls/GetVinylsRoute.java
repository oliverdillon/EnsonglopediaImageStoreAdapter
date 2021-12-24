package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.vinyls;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.AppendImageLocProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.GetVinylProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GetVinylsRoute extends RouteBuilder {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public void configure() throws Exception {
        from("direct:getVinylsEndpoint")
                .to("sql:select vinyl_id, artist_name, album_title, release_year from vinyls.albums" +
                        " inner join vinyls.artists on vinyls.albums.artist_id=vinyls.artists.artist_id")
//                .to("log:"+VinylsRoute.class.getName()+"?level=DEBUG")
                .process(new GetVinylProcessor())
                .process(new AppendImageLocProcessor(jdbcTemplate))
                .marshal().json(JsonLibrary.Jackson)
                .to("mock:vinyls");
//        select vinyls.albums.vinyl_id, artist_name, album_title, release_year, image_loc from vinyls.albums inner join vinyls.artists on vinyls.albums.artist_id=vinyls.artists.artist_id inner join vinyls.images on vinyls.albums.vinyl_id=vinyls.images.vinyl_id
    }
}
