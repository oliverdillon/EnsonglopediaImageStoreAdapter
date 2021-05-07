package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class VinylProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String json = exchange.getMessage().getBody(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Vinyl vinyl = objectMapper.readValue(json, Vinyl.class);
        exchange.getMessage().setBody(vinyl);
        System.out.println(vinyl.getAlbum());
    }
}
