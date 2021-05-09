package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class VinylStoreProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        File jsonFile = new File("files/input/data.json");
        ObjectMapper objectMapper = new ObjectMapper();

        //preexisting vinyls
        String json = FileUtils.readFileToString(jsonFile);
        VinylsResponse vinyls = objectMapper.readValue(json, VinylsResponse.class);

        //vinyls to add
        String request = exchange.getMessage().getBody(String.class);
        Vinyl vinylNew = objectMapper.readValue(request,Vinyl.class);
        boolean exists = vinyls.getData().stream()
                .anyMatch(vinyl -> vinyl.getId()==(vinylNew.getId()));

        if(!exists) {
            vinyls.getData().add(vinylNew);
        }
        else{
            List<Vinyl> vinylsData = vinyls.getData().stream()
                    .map(vinyl -> vinyl=(vinyl.getId()==(vinylNew.getId()))? vinylNew:vinyl)
                    .collect(Collectors.toList());
            vinyls.setData(vinylsData);
        }
        exchange.getMessage().setBody(vinyls);
    }
}

