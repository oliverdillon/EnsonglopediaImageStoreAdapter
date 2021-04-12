package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PingEndpointRoute extends RouteBuilder {
    private final Environment env;


    public PingEndpointRoute(Environment env) {
        this.env = env;
    }

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .contextPath(env.getProperty("camel.component.servlet.mapping.contextPath", "/rest/*"))
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Spring Boot Camel Postgres Rest API.")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .port(env.getProperty("server.port", "8080"));

        rest("/ping")
                .get().route()
                .process(new SimpleLoggingProcessor())
                .to("log:ping-endpoint");

    }
}
