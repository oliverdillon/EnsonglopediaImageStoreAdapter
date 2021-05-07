package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Routes;

import com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes.FileProcessor;
import com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes.SimpleLoggingProcessor;
import com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes.Vinyl;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;

//@Component
public class WebserverRoute extends RouteBuilder {
    private final Environment env;

//    @Autowired
//    private ReadFileRoute readFileRoute;

    public WebserverRoute(Environment env) {
        this.env = env;
    }

    private static final String AUTH_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Authentication succeeded.\""
            + "\"token\": \"%s\""
            + "}";

    private static final String OPERATION_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded.\""
            + "\"token\": \"%s\""
            + "}";

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
                .port(env.getProperty("server.port", "8080"));

        rest("/ping")
                .get().route()
                .process(new SimpleLoggingProcessor())
                .setBody(constant(OPERATION_SUCCEEDED))
                .end();
        rest("/vinyls")
                .get().route()
//                .streamCaching()
                .process(new SimpleLoggingProcessor())
//                .transform(simple("files/input/data.json",java.io.File.class))
                .setBody(simple("file:files/input/?fileName=data.json"))
                .marshal(new ListJacksonDataFormat(Vinyl.class))
                .log("Check message: ${body}")
                .process(new FileProcessor())
                .end();
//                .log("${body}")
//                .unmarshal(new ListJacksonDataFormat(Vinyl.class))
//                .process(new SimpleLoggingProcessor())
//                .marshal().json()


        rest("/images")
                .consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
                .consumes(MediaType.IMAGE_JPEG_VALUE)
                .produces(MediaType.IMAGE_JPEG_VALUE)
                .post().route()
                .process(new SimpleLoggingProcessor())
                .to("file:files/images?fileName=${header.FileName}.jpeg")
                .setBody(constant(OPERATION_SUCCEEDED)).end();

        rest("/text")
                .consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
                .consumes(MediaType.TEXT_PLAIN_VALUE)
                .produces(MediaType.TEXT_PLAIN_VALUE)
                .post().route()
                .process(new SimpleLoggingProcessor())
                .to("file:files/text?fileName=${header.FileName}.txt")
                .setBody(constant(OPERATION_SUCCEEDED)).end();
    }
}
