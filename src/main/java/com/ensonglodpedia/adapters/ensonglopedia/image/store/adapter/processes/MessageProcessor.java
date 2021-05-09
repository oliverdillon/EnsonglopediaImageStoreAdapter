package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MessageProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getMessage().getBody(String.class);
        String filename = exchange.getMessage().getHeader("Filename",String.class);

        if(filename.contains("."))
            body = body.replaceAll("REPLACE_ASSET_LOCATION","/assets/"+filename);
        else
            body = body.replaceAll("REPLACE_ASSET_LOCATION","/assets/"+filename+".jpeg");

        body = body.replaceAll("REPLACE_ASSET_LOCATION","/assets/"+filename);
        exchange.getMessage().setBody(body);
    }
}
