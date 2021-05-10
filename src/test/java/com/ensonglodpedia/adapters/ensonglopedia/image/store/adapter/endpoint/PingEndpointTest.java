package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ContextConfiguration
public class PingEndpointTest {

//    @Autowired
//    protected CamelContext camelContext;

    @EndpointInject("mock:ping")
    protected MockEndpoint ping;

    @Autowired
    ProducerTemplate template;

    private static final String OPERATION_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded.\","
            + "\"token\": \"%s\""
            + "}";

    @Test
    @DirtiesContext
    public void shouldReturnDefaultMessage() throws Exception {
        ping.expectedBodiesReceived(OPERATION_SUCCEEDED);
        template.sendBody("direct:pingEndpoint",null);
        ping.assertIsSatisfied();
    }
}