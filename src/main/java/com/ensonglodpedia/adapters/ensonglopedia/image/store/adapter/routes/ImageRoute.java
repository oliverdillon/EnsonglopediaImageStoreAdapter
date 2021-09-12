package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.legacy.LocalImagePostRequestProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.IMAGE_POST_SUCCEEDED;
import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.IMAGE_POST_FAILURE;

@Component
public class ImageRoute extends RouteBuilder {

    @Value("${image.output.directory}")
    private String fileDirectory;

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
                        .to("file:"+fileDirectory+"?fileName=${header.Filename}")
                        .setBody(constant(IMAGE_POST_SUCCEEDED))
                    .when(header("filename").regex("^.*\\..*$"))
                        .setBody(constant(IMAGE_POST_FAILURE))
                    .otherwise()
                        .to("file:"+fileDirectory+"?fileName=${header.Filename}.jpeg")
                        .setBody(constant(IMAGE_POST_SUCCEEDED))
                .end()
                .process(new LocalImagePostRequestProcessor())
                .to("mock:images");
    }
}
