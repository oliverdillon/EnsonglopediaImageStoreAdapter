package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.endpoint;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TextEnpointTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String Default_message = "{Hello there}";

    @BeforeEach
    public void clearDirectories(){

    }

    @Test
    public void getShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/rest/ping"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(Default_message)));
    }

    @Test
    public void postShouldReturnDefaultMessage() throws Exception {
        File textFile = new File("src/test/files/Test_File.txt");
        byte[] bytes = FileUtils.readFileToByteArray(textFile);
        this.mockMvc.perform(post("/rest/text").content(bytes))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
