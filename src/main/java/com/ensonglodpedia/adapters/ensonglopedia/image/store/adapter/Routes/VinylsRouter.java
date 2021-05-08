package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.Routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.VinylProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class VinylsRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:getVinylsEndpoint")
                .setProperty("Log", constant("Retrieving vinyl data"))
                .process(new SimpleLoggingProcessor())
                .transform(simple("files/input/data.json",java.io.File.class))
                .convertBodyTo(String.class)
                .process(new VinylProcessor())
//                    .marshal(vinylRequestDataFormat)
                .log("${body.getData().get(1).getArtist()}")
                .marshal().json(JsonLibrary.Jackson)
                .to("mock:vinyls");

        from("direct:postVinylsEndpoint")
                .setProperty("Log", constant("Retrieving vinyl data"))
                .process(new SimpleLoggingProcessor())
                .convertBodyTo(String.class)
                .to("mock:vinyls");
    }
}
