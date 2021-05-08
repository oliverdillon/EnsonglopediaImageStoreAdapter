package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.AbstractTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
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
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest extends AbstractTest {

    private static final String OPERATION_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded.\""
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
        File imageDirectory = new File("files/images");
        File[] imageFiles = imageDirectory.listFiles();
        File textDirectory = new File("files/text");
        File[] textFiles = textDirectory.listFiles();
        for(File file:imageFiles){
            file.delete();
        }
        for(File file:textFiles){
            file.delete();
        }
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        String check = this.restTemplate.getForObject("http://localhost:" + port + "/rest/ping",
                String.class);
        assertThat(check).contains(OPERATION_SUCCEEDED);
    }

    @Test
    public void postTextShouldReturnDefaultMessage() throws Exception {
        File textFile = new File("files/input/Test_File.txt");
        byte[] bytes = FileUtils.readFileToByteArray(textFile);
        String fileName = "Hello";

        headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.add("FileName",fileName);
        HttpEntity request = new HttpEntity(bytes, headers);

        String check = this.restTemplate.postForObject("http://localhost:" + port + "/rest/text",
                request,String.class);
        assertThat(check).contains(OPERATION_SUCCEEDED);

        Thread.sleep(10_000L);
        File storedFile = new File("files/text/"+fileName+".txt");
        assertTrue(storedFile.exists());
    }

    @Test
    public void postImageShouldReturnDefaultMessage() throws Exception {
        File imageFile = new File("files/input/Hotel_California_Back.jpeg");
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);
        String fileName = "Hotel_California_Back";

        headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.add("FileName",fileName);
        HttpEntity request = new HttpEntity(bytes, headers);

        String check = this.restTemplate.postForObject("http://localhost:" + port + "/rest/images",
                request,String.class);
        assertThat(check).contains(OPERATION_SUCCEEDED);

        Thread.sleep(10_000L);
        File storedFile = new File("files/images/"+fileName+".jpeg");
        assertTrue(storedFile.exists());
    }

    @Test
    public void getVinylShouldReturnDefaultMessage() throws Exception {
        String response = this.restTemplate.getForObject("http://localhost:" + port + "/rest/vinyls",String.class);

        String fileName = "input/data.json";
        String expectedContent = getStringFromResource(fileName)
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        ObjectMapper objectMapper = new ObjectMapper();
        assertEquals(objectMapper.readTree(expectedContent),objectMapper.readTree(response));
    }
}
