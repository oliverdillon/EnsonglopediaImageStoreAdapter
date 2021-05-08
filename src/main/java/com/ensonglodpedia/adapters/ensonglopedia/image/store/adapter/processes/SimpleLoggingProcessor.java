package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLoggingProcessor implements Processor {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String message = exchange.getProperty("Log", String.class);
        logger.info("SimpleLoggingProcessor: {}",message);
    }
}
