package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import java.io.InputStream;

public class SimpleFileProcessor implements Processor {
    private Logger logger = LoggerFactory.getLogger(SimpleFileProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        InputStream inputStream = exchange.getMessage().getBody(InputStream.class);
        Parser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        parser.parse(inputStream, handler, metadata, context);
        logger.info("SimpleFileProcessor File type: {}", metadata.get("Content-Type"));
        exchange.getMessage().setBody(inputStream);
    }
}
