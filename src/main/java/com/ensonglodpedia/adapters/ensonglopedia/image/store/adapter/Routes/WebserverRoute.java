package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.Routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.VinylsResponse;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class WebserverRoute extends RouteBuilder {
    private final Environment env;

    public WebserverRoute(Environment env) {
        this.env = env;
    }

    @Override
    public void configure() throws Exception {
        // enable Jackson json type converter
        getContext().getGlobalOptions().put("CamelJacksonEnableTypeConverter", "true");
        // allow Jackson json to convert to pojo types also (by default jackson only converts to String and other simple types)
        getContext().getGlobalOptions().put("CamelJacksonTypeConverterToPojo", "true");

        restConfiguration()
                .contextPath(env.getProperty("camel.component.servlet.mapping.contextPath", "/rest/*"))
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Spring Boot Camel Rest API.")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .port(env.getProperty("server.port", "8080"))
                .enableCORS(true) // <-- Important
                .corsAllowCredentials(true) // <-- Important
                .corsHeaderProperty("Access-Control-Allow-Origin","http://localhost:4200")
                .corsHeaderProperty("Access-Control-Allow-Headers",
                        "Origin, Accept, X-Requested-With, " +
                                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, " +
                                "Authorization, Filename");;

        rest("/ping")
                .get().route()
                .to("direct:pingEndpoint")
                .endRest();

        rest("/vinyls")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .get()
                    .route()
                    .to("direct:getVinylsEndpoint")
                .endRest()

                .post().type(VinylsResponse.class)
                    .route()
                    .to("direct:postVinylsEndpoint")
                    .endRest();

        rest("/images")
                .consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
                .consumes(MediaType.IMAGE_JPEG_VALUE)
                .produces(MediaType.IMAGE_JPEG_VALUE)
                .get().route()
                .to("direct:getImageEndpoint")
                .endRest()
                .post().route()
                .to("direct:postImageEndpoint")
                .endRest();

        rest("/text")
                .consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
                .consumes(MediaType.TEXT_PLAIN_VALUE)
                .produces(MediaType.TEXT_PLAIN_VALUE)
                .post().route()
                .to("direct:textEndpoint")
                .endRest();
    }
}
