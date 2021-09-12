package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.legacy;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.legacy.VinylsLegacyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class LocalVinylRetrievalProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getMessage().getBody(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        VinylsLegacyResponse vinyls = objectMapper.readValue(json, VinylsLegacyResponse.class);
        exchange.getMessage().setBody(vinyls);
    }
}
