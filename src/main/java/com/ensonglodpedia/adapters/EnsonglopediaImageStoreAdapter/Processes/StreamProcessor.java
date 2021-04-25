package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes;

import org.apache.camel.Exchange;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        try (final InputStream is = exchange.getMessage().getBody(InputStream.class);
             final OutputStream os = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            for (byte b: buffer) {
                os.write(b);
            }
            exchange.getMessage().setBody(os, OutputStream.class);
        }
    }
}
