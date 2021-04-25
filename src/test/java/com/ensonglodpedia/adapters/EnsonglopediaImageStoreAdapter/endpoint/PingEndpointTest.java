package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.endpoint;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class PingEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String AUTH_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Authentication succeeded.\""
            + "\"token\": \"%s\""
            + "}";

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/rest/ping")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(AUTH_SUCCEEDED)));
    }
}