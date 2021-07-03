package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.Vinyl;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.Vinyls;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.VinylRetrievalProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.VinylStoreProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.VinylsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.OPERATION_SUCCEEDED;

@Component
public class VinylsRoute extends RouteBuilder {

    @Autowired
    public SqlComponent sql;

    public String uuid = UUID.randomUUID().toString();

    @Override
    public void configure() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonDataFormat vinylRequestDataFormat= new JacksonDataFormat(objectMapper, VinylsResponse.class);

        from("direct:getVinylsEndpoint")
                .setProperty("Log", constant("Retrieving vinyl data"))
                .process(new SimpleLoggingProcessor())
                .transform(simple("files/input/data.json",java.io.File.class))
                .convertBodyTo(String.class)
                .process(new VinylRetrievalProcessor())
//                    .marshal(vinylRequestDataFormat)
                .marshal().json(JsonLibrary.Jackson)
                .to("mock:vinyls");

        from("direct:getVinylsEndpoint")
                .to("sql:select artist_name, album_title, year from vinyls.albums" +
                        "inner join vinyls.artists on vinyls.albums.artist_id=vinyls.artists.artist_id" +
                        "where vinyl_id ='"+uuid+"';")
                .to("log:"+ Vinyl.class.getName());

        from("direct:postVinylsEndpoint")
                .setProperty("Log", constant("Adding to vinyl data"))
                .process(new SimpleLoggingProcessor())
                .process(new VinylStoreProcessor())
                .marshal().json(JsonLibrary.Jackson)
                .to("file:files/input/?fileName=data.json")
                .setBody(constant(OPERATION_SUCCEEDED))
                .to("mock:vinyls");

        from("direct:testEndpoint")
                .to("sql:select vinyls.add_vinyl('0b2f7a82-d1d7-11eb-ae32-06d3dce85271'," +
                        "'0b2f7a82-d1d7-11eb-ae32-06d3dce85279'," +
                        "'Prince'," +
                        "'Purple Rain'," +
                        "1984)");

        from("sql:select vinyl_id from vinyls.vinyls;").to(
                 "log:"+ Vinyls.class.getName()+"?level=INFO");

    }
}
