package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.AbstractTest;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.EnsonglopediaImageStoreAdapterApplication;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.IntegrationConfig;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.TestIntegration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestIntegration.class,
        IntegrationConfig.class, EnsonglopediaImageStoreAdapterApplication.class})

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HttpRequestTest extends AbstractTest {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    private static final String OPERATION_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded\","
            + "\"token\": \"%s\""
            + "}";

    private static final String IMAGE_DETAILS = "{"
            + "\"success\":true,"
            + "\"message\":\"Operation succeeded\","
            + "\"location\":\"REPLACE_ASSET_LOCATION\","
            + "\"token\":\"%s\""
            + "}";

    private String vinyl_id_1 = UUID.randomUUID().toString();
    private String vinyl_id_2 = UUID.randomUUID().toString();
    private String vinyl_id_3 = UUID.randomUUID().toString();
    private String vinyl_id_4 = UUID.randomUUID().toString();
    private String vinyl_id_5 = UUID.randomUUID().toString();

    private String expectedContent = "{\n" +
            "  \"data\":[\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_1+"\",\n" +
            "      \"artist_name\": \"\",\n" +
            "      \"album_title\": \"\",\n" +
            "      \"release_year\": \"0\",\n" +
            "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_2+"\",\n" +
            "      \"artist_name\": \"Prince\",\n" +
            "      \"album_title\": \"Purple Rain\",\n" +
            "      \"release_year\": \"1984\",\n" +
            "      \"imgs\": [\"/assets/Purple_Rain.jpg\",\"/assets/Purple_Rain_Back.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_3+"\",\n" +
            "      \"artist_name\": \"Finneas\",\n" +
            "      \"album_title\": \"Blood Harmony\",\n" +
            "      \"release_year\": \"2019\",\n" +
            "      \"imgs\": [\"/assets/Blood_Harmony.png\",\"/assets/Blood_Harmony_Back.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_4+"\",\n" +
            "      \"artist_name\": \"Eagles\",\n" +
            "      \"album_title\": \"Hotel California\",\n" +
            "      \"release_year\": \"1976\",\n" +
            "      \"imgs\": [\"/assets/Hotel_California.jpg\",\"/assets/Hotel_California_Back.jpg\"]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_5+"\",\n" +
            "      \"artist_name\": \"Eagles\",\n" +
            "      \"album_title\": \"Eagles\",\n" +
            "      \"release_year\": \"1972\",\n" +
            "      \"imgs\": [\"/assets/Eagles.jpeg\"]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers;

    @Inject
    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() throws Exception {
        add_vinyl(vinyl_id_1,"", "",
                0, "/assets/Add_Record_Button.jpg");
        add_vinyl(vinyl_id_2,"Prince", "Purple Rain",
                1984, "/assets/Purple_Rain.jpg", "/assets/Purple_Rain_Back.jpg");
        add_vinyl(vinyl_id_3, "Finneas", "Blood Harmony",
                2019,"/assets/Blood_Harmony.png", "/assets/Blood_Harmony_Back.jpg");
        add_vinyl(vinyl_id_4, "Eagles", "Hotel California",
                1976,"/assets/Hotel_California.jpg", "/assets/Hotel_California_Back.jpg");
        add_vinyl(vinyl_id_5,"Eagles", "Eagles",
                1972, "/assets/Eagles.jpeg");
    }

    public void add_vinyl(String vinyl_id, String artist_name, String album_title, int release_year,
                          String... image_loc_list){
        String artist_id = UUID.randomUUID().toString();

        jdbcTemplate
                .update("insert into vinyls.vinyls(vinyl_id) values (?)",
                        vinyl_id);
        jdbcTemplate
                .update("insert into vinyls.artists(artist_id, artist_name) values (?, ?)",
                        artist_id,artist_name);
        jdbcTemplate
                .update("insert into vinyls.albums(vinyl_id, album_title, release_year, artist_id) values (?,?,?,?)",
                        vinyl_id,album_title,release_year,artist_id);

        for (String image_loc : image_loc_list){
            String image_id = UUID.randomUUID().toString();
            jdbcTemplate
                    .update("insert into vinyls.images(image_id,image_loc,vinyl_id ) values (?,?,?)",
                            image_id, image_loc, vinyl_id);

        }
    }

    @After
    public void tearDown() throws Exception {
        jdbcTemplate.execute("delete from vinyls.images");
        jdbcTemplate.execute("delete from vinyls.songs");
        jdbcTemplate.execute("delete from vinyls.albums");
        jdbcTemplate.execute("delete from vinyls.artists");
        jdbcTemplate.execute("delete from vinyls.vinyls");
    }

    @Before
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

    @Before
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
        headers.add("vinyl_id",vinyl_id_1);
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
    public void getLegacyVinylShouldReturnDefaultMessage() throws Exception {
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
    public void postLegacyVinylShouldReturnDefaultMessage(){
        String body = "{\n" +
                "      \"vinyl_id\": \"5\",\n" +
                "      \"artist_name\": \"Hello\",\n" +
                "      \"album_title\": \"example album\",\n" +
                "      \"release_year\": \"2020\",\n" +
                "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
                "    }";

        HttpEntity request = new HttpEntity(body);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/rest/legacyVinyls",
                request,String.class);

        assertThat(response).contains(OPERATION_SUCCEEDED);

    }

    @Test
    public void postLegacyVinylShouldOverwrite(){
        String body = "{\n" +
                "      \"vinyl_id\": \"1\",\n" +
                "      \"artist_name\": \"Hello\",\n" +
                "      \"album_title\": \"example album\",\n" +
                "      \"release_year\": \"2020\",\n" +
                "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
                "    }";

        HttpEntity request = new HttpEntity(body);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/rest/legacyVinyls",
                request,String.class);

        assertThat(response).contains(OPERATION_SUCCEEDED);

    }

    @Test
    public void getVinylShouldReturnDefaultMessage() throws Exception {
        String response = this.restTemplate.getForObject("http://localhost:" + port + "/rest/vinyls",String.class);

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
        HttpHeaders headers = new HttpHeaders();
        headers.add("Artist_Name","Hello");
        headers.add("album_title","example album");
        headers.add("release_year","2020");
        HttpEntity request = new HttpEntity(null,headers);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/rest/vinyls",
                request,String.class);

        assertThat(response).contains(OPERATION_SUCCEEDED);

    }

}
