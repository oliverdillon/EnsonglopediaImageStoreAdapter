package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.vinyls;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetVinylsRoute extends RouteBuilder {
//    @Autowired
//    public SqlComponent sql;

    @Override
    public void configure() throws Exception {
        from("direct:testGetEndpoint")
                .to("sql:select artist_name, album_title, year from vinyls.albums" +
                        " inner join vinyls.artists on vinyls.albums.artist_id=vinyls.artists.artist_id" +
                        " where vinyl_id ='0b2f7a82-d1d7-11eb-ae32-06d3dce85271';")
//                .to("log:"+VinylsRoute.class.getName()+"?level=DEBUG")
                .log("${body}")
                .to("mock:vinyls");
    }
}
