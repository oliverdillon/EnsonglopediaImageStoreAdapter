package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getMessage().getBody();
    }
}
