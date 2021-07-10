package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.local;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.local.LocalVinylRetrievalProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.local.LocalVinylStoreProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.VinylsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.OPERATION_SUCCEEDED;

@Component
public class LocalVinylsRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonDataFormat vinylRequestDataFormat= new JacksonDataFormat(objectMapper, VinylsResponse.class);

        from("direct:getVinylsLegacyEndpoint")
                .setProperty("Log", constant("Retrieving vinyl data"))
                .process(new SimpleLoggingProcessor())
                .transform(simple("files/input/data.json",java.io.File.class))
                .convertBodyTo(String.class)
                .process(new LocalVinylRetrievalProcessor())
//                    .marshal(vinylRequestDataFormat)
                .marshal().json(JsonLibrary.Jackson)
                .to("mock:legacyVinyls");

        from("direct:postVinylsLegacyEndpoint")
                .setProperty("Log", constant("Adding to vinyl data"))
                .process(new SimpleLoggingProcessor())
                .process(new LocalVinylStoreProcessor())
                .marshal().json(JsonLibrary.Jackson)
                .to("file:files/input/?fileName=data.json")
                .setBody(constant(OPERATION_SUCCEEDED))
                .to("mock:legacyVinyls");

    }
}
