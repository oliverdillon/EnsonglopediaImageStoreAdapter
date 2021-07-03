package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SimpleLoggingProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.OPERATION_SUCCEEDED;
@Component
public class PingRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:pingEndpoint")
                .setProperty("Log", constant("Pinging route"))
                .process(new SimpleLoggingProcessor())
                .setBody(constant(OPERATION_SUCCEEDED))
                .to("mock:ping");
    }
}
