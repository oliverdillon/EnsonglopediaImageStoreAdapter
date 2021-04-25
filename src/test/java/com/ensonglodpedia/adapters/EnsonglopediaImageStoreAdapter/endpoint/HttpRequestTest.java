package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.endpoint;

import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    private static final String AUTH_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Authentication succeeded.\""
            + "\"token\": \"%s\""
            + "}";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers;

    @BeforeEach
    public void clearDirectories(){
        List<File> filesToDelete = new LinkedList<>();
        filesToDelete.add( new File("files/images/MyFile.jpg"));
        filesToDelete.add( new File("files/text/MyFile.txt"));
        filesToDelete.forEach(file -> file.delete());
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/rest/ping",
                String.class)).contains(AUTH_SUCCEEDED);
    }

    @Test
    public void postTextShouldReturnDefaultMessage() throws Exception {
        File textFile = new File("files/input/Test_File.txt");
        byte[] bytes = FileUtils.readFileToByteArray(textFile);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity request = new HttpEntity(bytes, headers);

        String check = this.restTemplate.postForObject("http://localhost:" + port + "/rest/text",request,String.class);
    }

    @Test
    public void postImageShouldReturnDefaultMessage() throws Exception {
        File textFile = new File("files/input/Hotel_California_Back.jpeg");
        byte[] bytes = FileUtils.readFileToByteArray(textFile);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        HttpEntity request = new HttpEntity(bytes, headers);

        String check = this.restTemplate.postForObject("http://localhost:" + port + "/rest/images",request,String.class);
    }
}
