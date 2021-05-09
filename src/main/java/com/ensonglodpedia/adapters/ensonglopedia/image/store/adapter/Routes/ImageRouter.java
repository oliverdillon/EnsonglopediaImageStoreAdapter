package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.Routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.OPERATION_SUCCEEDED;

@Component
public class ImageRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:getImageEndpoint")
                .setProperty("Log", constant("Retrieving vinyl image data for: ${header.Filename}"))
                .process(new SimpleLoggingProcessor())
                .setBody(simple("files/images/${header.Filename}",java.io.File.class))
                .to("mock:images");

        from("direct:postImageEndpoint")
                .setProperty("Log", constant("Storing vinyl image data for: ${header.Filename}"))
                .process(new SimpleLoggingProcessor())
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
