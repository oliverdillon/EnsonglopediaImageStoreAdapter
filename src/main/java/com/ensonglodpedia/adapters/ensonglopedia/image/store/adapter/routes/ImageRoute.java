package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.PostImageLocProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.PostImageResponseProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class ImageRoute extends RouteBuilder {

    @Value("${image.output.directory}")
    private String fileDirectory;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public void configure() throws Exception {
        from("direct:getImageEndpoint")
                .setProperty("Log", constant("Retrieving vinyl image data for: ${header.Filename}"))
                .process(new SimpleLoggingProcessor())
                .setBody(simple("files/images/${header.Filename}",java.io.File.class))
                .to("mock:images");

        from("direct:postImageEndpoint")
                .validate(header("vinyl_id").isNotNull())
                .validate(header("Filename").isNotNull())
                .setProperty("Log", simple("Storing vinyl image data for: ${header.Filename}"))
                .process(new SimpleLoggingProcessor())
                .choice()
                    .when(header("filename").regex("^.*\\.(jpg|jpeg|JPG|png)$"))
                        .to("file:"+fileDirectory+"?fileName=${header.Filename}")
                        .setProperty("Success",constant(true))
                    .when(header("filename").regex("^.*\\..*$"))
                        .setProperty("Success",constant(false))
                    .otherwise()
                        .to("file:"+fileDirectory+"?fileName=${header.Filename}.jpeg")
                        .setProperty("Success",constant(true))
                .end()
                .process(new PostImageResponseProcessor())
                .choice()
                    .when(exchangeProperty("Success").isEqualTo(true))
                    .process(new PostImageLocProcessor(jdbcTemplate))
                .end()
                .marshal().json(JsonLibrary.Jackson)
                .to("mock:images");
    }
}
