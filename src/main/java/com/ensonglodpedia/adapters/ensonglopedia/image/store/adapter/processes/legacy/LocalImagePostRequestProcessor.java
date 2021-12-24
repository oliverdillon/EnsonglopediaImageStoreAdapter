package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.legacy;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.ImagePostResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class LocalImagePostRequestProcessor implements Processor {

    private ImagePostResponse body;

    @Override
    public void process(Exchange exchange) throws Exception {

        Boolean success = exchange.getProperty("Success",Boolean.class);
        String filename = exchange.getMessage().getHeader("Filename",String.class);
        if(success){
            body = new ImagePostResponse(success, "Operation succeeded","%s");
        }
        else{
            body = new ImagePostResponse(success, "Operation failed","%s");
        }

        if(filename.contains(".")) {
            body.setLocation( "/assets/" + filename);
        }
        else {
            body.setLocation( "/assets/" + filename + ".jpeg");
        }
        exchange.getMessage().setBody(body);
    }
}
