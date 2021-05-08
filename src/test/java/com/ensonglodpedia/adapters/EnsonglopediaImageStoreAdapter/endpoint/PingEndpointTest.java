package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.endpoint;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PingEndpointTest {

    @Autowired
    protected CamelContext camelContext;

    @EndpointInject("mock:ping")
    protected MockEndpoint ping;


    private static final String AUTH_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Authentication succeeded.\""
            + "\"token\": \"%s\""
            + "}";

//    @Test
//    public void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/rest/ping")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString(AUTH_SUCCEEDED)));
//    }
}