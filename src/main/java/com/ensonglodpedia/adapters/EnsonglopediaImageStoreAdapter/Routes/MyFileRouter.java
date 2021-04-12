package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Routes;

import org.apache.camel.builder.RouteBuilder;

public class MyFileRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("files:files/input")
                .log("${body}")
                .to("file:files/output");
    }
}
