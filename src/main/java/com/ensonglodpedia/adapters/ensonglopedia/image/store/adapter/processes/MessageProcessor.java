package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;

public class MessageProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getMessage().getBody(String.class);
        String filename = exchange.getMessage().getHeader("Filename",String.class);
        File dir = new File(".");
        String path = dir.getAbsolutePath()
                .replaceAll("\\\\","/")
                .replace(".","");

        if(filename.contains("."))
            body = body.replaceAll("REPLACE_ASSET_LOCATION",path+filename);
        else
            body = body.replaceAll("REPLACE_ASSET_LOCATION",path+filename+".jpeg");

        exchange.getMessage().setBody(body);
    }
}
