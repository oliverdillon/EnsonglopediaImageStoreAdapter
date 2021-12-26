package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.legacy;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.legacy.VinylLegacy;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.legacy.VinylsLegacyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class LocalVinylStoreProcessor implements Processor {

    private String fileDirectory;

    public LocalVinylStoreProcessor(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        File jsonFile = new File(fileDirectory);
        ObjectMapper objectMapper = new ObjectMapper();

        //preexisting vinyls
        String json = FileUtils.readFileToString(jsonFile);
        VinylsLegacyResponse vinyls = objectMapper.readValue(json, VinylsLegacyResponse.class);

        //vinyls to add
        String request = exchange.getMessage().getBody(String.class);
        VinylLegacy vinylNew = objectMapper.readValue(request,VinylLegacy.class);
        boolean exists = vinyls.getData().stream()
                .anyMatch(vinyl -> vinyl.getVinyl_id().equals(vinylNew.getVinyl_id()));

        if(!exists) {
            vinyls.getData().add(vinylNew);
        }
        else{
            List<VinylLegacy> vinylsData = vinyls.getData().stream()
                    .map(vinyl -> vinyl=(vinyl.getVinyl_id().equals(vinylNew.getVinyl_id()))? vinylNew:vinyl)
                    .collect(Collectors.toList());
            vinyls.setData(vinylsData);
        }
        exchange.getMessage().setBody(vinyls);
    }
}

