package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.Routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.MessageProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.IMAGE_POST_SUCCEEDED;
import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.IMAGE_POST_FAILURE;

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
                .setProperty("Log", simple("Storing vinyl image data for: ${header.Filename}"))
                .process(new SimpleLoggingProcessor())
                .choice()
                    .when(header("filename").regex("^.*\\.(jpg|jpeg|JPG|png)$"))
                        .to("file:files/images?fileName=${header.Filename}")
                        .setBody(constant(IMAGE_POST_SUCCEEDED))
                    .when(header("filename").regex("^.*\\..*$"))
                        .setBody(constant(IMAGE_POST_FAILURE))
                    .otherwise()
                        .to("file:files/images?fileName=${header.Filename}.jpeg")
                        .setBody(constant(IMAGE_POST_SUCCEEDED))
                .end()
                .process(new MessageProcessor())
                .to("mock:images");
    }
}
