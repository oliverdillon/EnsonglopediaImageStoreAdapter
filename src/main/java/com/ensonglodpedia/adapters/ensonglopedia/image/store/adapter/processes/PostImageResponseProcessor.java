package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models.ImagePostResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class PostImageResponseProcessor implements Processor {

    private ImagePostResponse body;

    @Override
    public void process(Exchange exchange) throws Exception {
        Boolean success = exchange.getProperty("Success",Boolean.class);
        String filename = exchange.getProperty("filename",String.class);

        if(success){
            body = new ImagePostResponse(success, "Operation succeeded","%s");

            if(filename.contains(".")) {
                body.setLocation( "/assets/" + filename);
            }
            else {
                body.setLocation( "/assets/" + filename + ".jpeg");
            }
        }
        else{
            body = new ImagePostResponse(success, "Operation failed","%s");
        }

        exchange.getMessage().setBody(body);
    }
}
