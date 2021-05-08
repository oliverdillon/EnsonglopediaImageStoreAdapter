package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.Routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.OPERATION_SUCCEEDED;

@Component
public class ImageRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:imagesEndpoint")
                .setProperty("Log", constant("Storing vinyl data"))
                .process(new SimpleLoggingProcessor())
                .to("file:files/images?fileName=${header.FileName}.jpeg")
                .setBody(constant(OPERATION_SUCCEEDED))
                .to("mock:images");
    }
}
