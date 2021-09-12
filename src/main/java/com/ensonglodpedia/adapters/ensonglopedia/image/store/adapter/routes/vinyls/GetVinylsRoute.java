package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.vinyls;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.GetVinylProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class GetVinylsRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:getVinylsEndpoint")
                .to("sql:select vinyl_id, artist_name, album_title, release_year from vinyls.albums" +
                        " inner join vinyls.artists on vinyls.albums.artist_id=vinyls.artists.artist_id")
//                .to("log:"+VinylsRoute.class.getName()+"?level=DEBUG")
                .process(new GetVinylProcessor())
                .marshal().json(JsonLibrary.Jackson)
                .to("mock:vinyls");
    }
}
