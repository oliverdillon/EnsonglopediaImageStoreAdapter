package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.vinyls;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostVinylsRoute extends RouteBuilder {

    public String vinyl_uuid = UUID.randomUUID().toString();

    public String artist_uuid = UUID.randomUUID().toString();

    @Override
    public void configure() throws Exception {
        from("direct:testPostEndpoint")
                .to("sql:select vinyls.add_vinyl('" +vinyl_uuid+"'," +
                        "'" +artist_uuid+"'," +
                        "'Prince'," +
                        "'Purple Rain'," +
                        "1984)");
    }
}
