package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.local;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.VinylsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class LocalVinylRetrievalProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getMessage().getBody(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        VinylsResponse vinyls = objectMapper.readValue(json, VinylsResponse.class);
        exchange.getMessage().setBody(vinyls);
    }
}
