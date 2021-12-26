package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.Vinyl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppendImageLocProcessor implements Processor {
    public JdbcTemplate jdbcTemplate;

    public AppendImageLocProcessor(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        List<Vinyl> vinylList = (List<Vinyl>)exchange.getMessage().getBody(List.class);
        for (Vinyl vinyl:vinylList)
        {
            String query = "select image_loc from vinyls.images where vinyls.images.vinyl_id='"+vinyl.getVinyl_id()+"'";

            List<Map<String, Object>> imageLocMaps = jdbcTemplate.queryForList(query);
            List<String> imageLocs = new ArrayList<>();

            for (Map<String, Object> imageLocMap: imageLocMaps){
                imageLocs.add((String) imageLocMap.get("image_loc"));
            }

            vinyl.setImgs(imageLocs);
        }
    }

}
