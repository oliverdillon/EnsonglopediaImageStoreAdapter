package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

public class PostVinylProcessor implements Processor {

    public JdbcTemplate jdbcTemplate;

    public PostVinylProcessor(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String vinyl_uuid = UUID.randomUUID().toString();
        String artist_uuid = UUID.randomUUID().toString();

        String artist = exchange.getMessage().getHeader("Artist_Name",String.class);
        String album = exchange.getMessage().getHeader("Album_Title",String.class);
        int year = exchange.getMessage().getHeader("Release_Year",int.class);

        String query = "call vinyls.add_vinyl('" +vinyl_uuid+"'," +
                "'" + artist_uuid + "'," +
                "'" + artist + "'," +
                "'" + album +"'," +
                "" + year + ")";

        jdbcTemplate.execute(query);
    }
}
