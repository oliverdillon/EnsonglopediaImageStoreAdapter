package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.Vinyl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

public class GetVinylProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String vinylData = exchange.getMessage().getBody(String.class);
//        vinylData = vinylData
//                .replaceAll(" ","")
//                .replaceAll("=","\\\":\\\"")
//                .replaceAll(",","\\\",\\\"")
//                .replaceAll("\\\"?\\{","\\{\\\"")
//                .replaceAll("\\}\\\"?","\\\"\\}")
//                .replaceAll("\"\"","\"\"");
        List<Vinyl> vinylObject = objectMapper.readValue(vinylData, new TypeReference<List<Vinyl>>(){});

        exchange.getMessage().setBody(vinylObject);
    }
}
