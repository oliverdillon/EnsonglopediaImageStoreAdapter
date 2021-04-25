package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Routes;

import com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes.ImagePojo;
import com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes.SimpleLoggingProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;

@Component
public class WebserverRoute extends RouteBuilder {
    private final Environment env;

    public WebserverRoute(Environment env) {
        this.env = env;
    }

    private static final String AUTH_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Authentication succeeded.\""
            + "\"token\": \"%s\""
            + "}";

    @Override
    public void configure() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(ImagePojo.class);
        DataFormat dataFormat = new JaxbDataFormat(jaxbContext);

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
                .setBody(constant(AUTH_SUCCEEDED))
                .end();

        rest("/images")
//                .consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
//                .consumes(MediaType.IMAGE_JPEG_VALUE)
//                .produces(MediaType.IMAGE_JPEG_VALUE)
                .post().route()
                .process(new SimpleLoggingProcessor())
                .to("file:files/images?fileName=MyFile.jpg").end();

        rest("/text")
                .post().route()
                .process(new SimpleLoggingProcessor())
                .to("file:files/text?fileName=MyFile.txt").end();

        rest("/detect")
                .consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
                .consumes(MediaType.IMAGE_JPEG_VALUE)
                .produces(MediaType.IMAGE_JPEG_VALUE)
                .post().route()
                .removeHeader("*")
                .process(new SimpleLoggingProcessor())
                .to("tika:parse")
                .to("mock:result").end();

    }
}
