package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.BytesDeserializer;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.ImagePostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class PostImageRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String body = exchange.getMessage().getBody(String.class);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ImagePostRequest.class, new BytesDeserializer());
        mapper.registerModule(module);
        ImagePostRequest vinylPostImageRequest = objectMapper.readValue(body, ImagePostRequest.class);


        exchange.setProperty("filename",vinylPostImageRequest.getFilename());
        exchange.setProperty("vinyl_uuid",vinylPostImageRequest.getVinyl_uuid());

        exchange.getMessage().setBody(vinylPostImageRequest.getFile(), byte[].class);
    }
}
