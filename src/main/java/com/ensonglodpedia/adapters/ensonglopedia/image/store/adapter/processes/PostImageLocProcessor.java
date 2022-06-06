package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.ImagePostResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

public class PostImageLocProcessor implements Processor {
    public JdbcTemplate jdbcTemplate;

    public PostImageLocProcessor(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String image_uuid = UUID.randomUUID().toString();

        String vinyl_uuid = exchange.getProperty("vinyl_uuid",String.class);
        ImagePostResponse imagePostResponse = exchange.getMessage().getBody(ImagePostResponse.class);

        String query = "insert into vinyls.images(image_id,image_loc,vinyl_id ) values ('" +image_uuid+"'," +
                "'" + imagePostResponse.getLocation() + "'," +
                "'" + vinyl_uuid + "')";

        jdbcTemplate.execute(query);
    }
}
