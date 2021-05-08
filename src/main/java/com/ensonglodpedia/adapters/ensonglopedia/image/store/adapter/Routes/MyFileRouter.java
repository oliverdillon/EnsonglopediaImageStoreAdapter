package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.Routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.VinylProcessor;
import org.apache.camel.builder.RouteBuilder;

import java.util.logging.Logger;

//@Component
public class MyFileRouter extends RouteBuilder {
    private final static Logger LOGGER = Logger.getLogger(MyFileRouter.class.getName());
    @Override
    public void configure() throws Exception {
        getContext().getGlobalOptions().put("CamelJacksonEnableTypeConverter", "true");
        // allow Jackson json to convert to pojo types also (by default jackson only converts to String and other simple types)
        getContext().getGlobalOptions().put("CamelJacksonTypeConverterToPojo", "true");

        from("file:files/input/?fileName=data.json&noop=true")
                .convertBodyTo(String.class)
                .process(new VinylProcessor())
                .to("mock:result").end();

//                .log("Check message: ${body}")
//                .end();

//        from("file:files/input/?fileName=Hotel_California_Back.jpeg&noop=true")
//                .process(new StreamProcessor())
//                .log("${body}")
//                .to("mock:result")
//                .to("file:files/output/?fileName=Hotel_California_Back_NEW.jpeg");
    }
}
