package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Routes;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class TimerRoute extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

    @Override
    public void configure() throws Exception {
        //timer
        //transform
        //log
        from("timer:first-timer")
                .log("${body}")
//                .transform().constant("My Constant value")
//                .transform().constant("Time now is "+ LocalDateTime.now())
                .bean(getCurrentTimeBean, "getCurrentTime")
                .log("${body}")
                .bean(simpleLoggingProcessingComponent )
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer");
    }
}

@Component
class GetCurrentTimeBean{
    public String getCurrentTime(){
        return "Time now is "+ LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent{
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);


    public void process(String message){
        logger.info("SimpleLoggingProcessingComponent {}",message);
    }
}
