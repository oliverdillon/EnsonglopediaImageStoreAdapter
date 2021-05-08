package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.AbstractTest;
import com.fasterxml.jackson.databind.JsonNode;
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

    private String expectedContent = "{\n" +
            "  \"data\":[\n" +
            "    {\n" +
            "      \"id\": 0,\n" +
            "      \"artist\": \"\",\n" +
            "      \"album\": \"\",\n" +
            "      \"date\": \"\",\n" +
            "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"artist\": \"Prince\",\n" +
            "      \"album\": \"Purple Rain\",\n" +
            "      \"date\": \"1984\",\n" +
            "      \"imgs\": [\"/assets/Purple_Rain.jpg\",\"/assets/Purple_Rain_Back.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"artist\": \"Finneas\",\n" +
            "      \"album\": \"Blood Harmony\",\n" +
            "      \"date\": \"2019\",\n" +
            "      \"imgs\": [\"/assets/Blood_Harmony.png\",\"/assets/Blood_Harmony_Back.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"artist\": \"Eagles\",\n" +
            "      \"album\": \"Hotel California\",\n" +
            "      \"date\": \"1976\",\n" +
            "      \"imgs\": [\"/assets/Hotel_California.jpg\",\"/assets/Hotel_California_Back.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"artist\": \"Eagles\",\n" +
            "      \"album\": \"Eagles\",\n" +
            "      \"date\": \"1972\",\n" +
            "      \"imgs\": [\"/assets/Eagles.jpeg\"]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

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
//        getStringFromResource(fileName)
        expectedContent = expectedContent
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedNode = objectMapper.readTree(expectedContent);
        JsonNode responseNode = objectMapper.readTree(response);
        assertEquals(expectedNode, responseNode);
    }
}
