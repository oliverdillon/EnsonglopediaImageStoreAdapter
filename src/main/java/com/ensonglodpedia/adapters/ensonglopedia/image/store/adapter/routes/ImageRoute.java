package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.PostImageLocProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.PostImageRequestProcessor;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.PostImageResponseProcessor;
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
        from("direct:postImageEndpoint")
                .process(new PostImageRequestProcessor())
                .setProperty("Log", simple("Storing vinyl image data for: ${exchangeProperty.filename}"))
                .choice()
                    .when(exchangeProperty("filename").regex("^.*\\.(jpg|jpeg|JPG|png)$"))
                        .to("file:"+fileDirectory+"?fileName=${exchangeProperty.filename}")
                        .setProperty("Success",constant(true))
                    .when(header("filename").regex("^.*\\..*$"))
                        .setProperty("Success",constant(false))
                    .otherwise()
                        .to("file:"+fileDirectory+"?fileName=${exchangeProperty.filename}.jpeg")
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
