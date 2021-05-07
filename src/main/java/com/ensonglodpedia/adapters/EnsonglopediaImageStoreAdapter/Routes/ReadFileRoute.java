package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Routes;

import org.apache.camel.builder.RouteBuilder;

public class ReadFileRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:readFromSampleFile").end();
    }
}
