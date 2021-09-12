package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.AbstractTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HttpRequestTest extends AbstractTest {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    private static final String OPERATION_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded.\","
            + "\"token\": \"%s\""
            + "}";

    private static final String IMAGE_DETAILS = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded.\","
            + "\"location\": \"REPLACE_ASSET_LOCATION\","
            + "\"token\": \"%s\""
            + "}";

    private static String expectedContent = "{\n" +
            "  \"data\":[\n" +
            "    {\n" +
            "      \"vinyl_id\": \"0\",\n" +
            "      \"artist_name\": \"\",\n" +
            "      \"album_title\": \"\",\n" +
            "      \"year\": \"\",\n" +
            "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \"1\",\n" +
            "      \"artist_name\": \"Prince\",\n" +
            "      \"album_title\": \"Purple Rain\",\n" +
            "      \"year\": \"1984\",\n" +
            "      \"imgs\": [\"/assets/Purple_Rain.jpg\",\"/assets/Purple_Rain_Back.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \"2\",\n" +
            "      \"artist_name\": \"Finneas\",\n" +
            "      \"album_title\": \"Blood Harmony\",\n" +
            "      \"year\": \"2019\",\n" +
            "      \"imgs\": [\"/assets/Blood_Harmony.png\",\"/assets/Blood_Harmony_Back.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \"3\",\n" +
            "      \"artist_name\": \"Eagles\",\n" +
            "      \"album_title\": \"Hotel California\",\n" +
            "      \"year\": \"1976\",\n" +
            "      \"imgs\": [\"/assets/Hotel_California.jpg\",\"/assets/Hotel_California_Back.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \"4\",\n" +
            "      \"artist_name\": \"Eagles\",\n" +
            "      \"album_title\": \"Eagles\",\n" +
            "      \"year\": \"1972\",\n" +
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

    @BeforeEach
    public void resetJsonData() throws IOException, InterruptedException {
        String fileName = "data.json";
        File jsonFile = new File("files/input/"+fileName);
        FileUtils.writeStringToFile(jsonFile, expectedContent);
        Thread.sleep(1_000L);
        logger.info("resetting json");
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        String check = this.restTemplate.getForObject("http://localhost:" + port + "/rest/ping",
                String.class);
        assertThat(check).contains(OPERATION_SUCCEEDED);
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
        String imageDetails = IMAGE_DETAILS.replaceAll("REPLACE_ASSET_LOCATION","/assets/"+fileName+".jpeg");

        String check = this.restTemplate.postForObject("http://localhost:" + port + "/rest/images",
                request,String.class);
        assertThat(check).contains(imageDetails);

        Thread.sleep(10_000L);
        File storedFile = new File("target/"+fileName+".jpeg");
        assertTrue(storedFile.exists());
    }

    @Test
    public void getVinylShouldReturnDefaultMessage() throws Exception {
        String response = this.restTemplate.getForObject("http://localhost:" + port + "/rest/legacyVinyls",String.class);

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

    @Test
    public void postVinylShouldReturnDefaultMessage(){
        String body = "{\n" +
                "      \"vinyl_id\": \"5\",\n" +
                "      \"artist_name\": \"Hello\",\n" +
                "      \"album_title\": \"example album\",\n" +
                "      \"year\": \"2020\",\n" +
                "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
                "    }";

        HttpEntity request = new HttpEntity(body);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/rest/legacyVinyls",
                request,String.class);

        assertThat(response).contains(OPERATION_SUCCEEDED);

    }

    @Test
    public void postVinylShouldOverwrite(){
        String body = "{\n" +
                "      \"vinyl_id\": \"1\",\n" +
                "      \"artist_name\": \"Hello\",\n" +
                "      \"album_title\": \"example album\",\n" +
                "      \"year\": \"2020\",\n" +
                "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
                "    }";

        HttpEntity request = new HttpEntity(body);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/rest/legacyVinyls",
                request,String.class);

        assertThat(response).contains(OPERATION_SUCCEEDED);

    }
}
