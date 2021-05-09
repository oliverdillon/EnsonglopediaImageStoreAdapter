package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.Routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.VinylRetrievalProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.VinylStoreProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.VinylsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class VinylsRouter extends RouteBuilder {
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
                .log("${body.getData().get(1).getArtist()}")
                .marshal().json(JsonLibrary.Jackson)
                .to("mock:vinyls");

        from("direct:postVinylsEndpoint")
                .setProperty("Log", constant("Adding to vinyl data"))
                .process(new SimpleLoggingProcessor())
                .process(new VinylStoreProcessor())
                .marshal().json(JsonLibrary.Jackson)
                .to("file:files/input/?fileName=data.json")
                .to("mock:vinyls");
    }
}
