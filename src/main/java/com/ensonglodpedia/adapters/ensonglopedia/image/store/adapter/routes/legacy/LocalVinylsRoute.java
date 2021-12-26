package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.legacy;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.legacy.LocalVinylRetrievalProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.legacy.LocalVinylStoreProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.OPERATION_SUCCEEDED;

@Component
public class LocalVinylsRoute extends RouteBuilder {

    @Value("${legacy.vinyl.file.path}")
    private String fileDirectory;

    @Value("${legacy.vinyl.file.uri}")
    private String fileUri;

    @Override
    public void configure() throws Exception {
        from("direct:getVinylsLegacyEndpoint")
                .setProperty("Log", constant("Retrieving vinyl data"))
                .process(new SimpleLoggingProcessor())
                .transform(simple(fileDirectory,java.io.File.class))
                .convertBodyTo(String.class)
                .process(new LocalVinylRetrievalProcessor())
                .marshal().json(JsonLibrary.Jackson)
                .to("mock:legacyVinyls");

        from("direct:postVinylsLegacyEndpoint")
                .setProperty("Log", constant("Adding to vinyl data"))
                .process(new SimpleLoggingProcessor())
                .process(new LocalVinylStoreProcessor(fileDirectory))
                .marshal().json(JsonLibrary.Jackson)
                .to("file:"+fileUri)
                .setBody(constant(OPERATION_SUCCEEDED))
                .to("mock:legacyVinyls");

    }
}
