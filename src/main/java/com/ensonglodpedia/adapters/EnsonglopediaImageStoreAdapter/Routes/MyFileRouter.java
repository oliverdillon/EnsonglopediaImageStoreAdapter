package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Routes;

import com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes.ImagePojo;
import com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes.SimpleFileProcessor;
import com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes.StreamProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;

import javax.xml.bind.JAXBContext;
//@Component
public class MyFileRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(ImagePojo.class);
        DataFormat dataFormat = new JaxbDataFormat(jaxbContext);
        from("file:files/input/?fileName=Hotel_California_Back.jpeg&noop=true")
                .process(new SimpleFileProcessor())
                .streamCaching()
                .process(new StreamProcessor())
//                .log("${body}")
                .to("mock:result")
                .to("file:files/output/?fileName=Hotel_California_Back_NEW.jpeg");
    }
}
