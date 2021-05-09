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
                .log("${header.Filename}")
                .choice()
                    .when(header("filename").regex("^.*\\.(jpg|jpeg|JPG|png)$"))
                        .to("file:files/images?fileName=${header.Filename}")
                        .setBody(constant(OPERATION_SUCCEEDED))
                    .when(header("filename").regex("^.*\\..*$"))
                        .setBody(constant("{Unknown file extension}"))
                    .otherwise()
                        .to("file:files/images?fileName=${header.Filename}.jpeg")
                        .setBody(constant(OPERATION_SUCCEEDED))
                .end()
                .to("mock:images");
    }
}
