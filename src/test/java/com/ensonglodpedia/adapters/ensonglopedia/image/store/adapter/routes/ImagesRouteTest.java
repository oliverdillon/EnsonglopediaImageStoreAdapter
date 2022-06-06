package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.IntegrationConfig;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.TestIntegration;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestIntegration.class,
        IntegrationConfig.class, ImageRoute.class})

@SpringBootTest
@ActiveProfiles("test")
public class ImagesRouteTest {

    @EndpointInject("mock:images")
    protected MockEndpoint imageMock;

    @Autowired
    ProducerTemplate template;

    @Inject
    private JdbcTemplate jdbcTemplate;

    private String vinyl_id_1 = UUID.randomUUID().toString();
    private String vinyl_id_2 = UUID.randomUUID().toString();


    private static final String IMAGE_DETAILS = "{"
            + "\"success\":true,"
            + "\"message\":\"Operation succeeded\","
            + "\"location\":\"REPLACE_ASSET_LOCATION\","
            + "\"token\":\"%s\""
            + "}";
    private static final String OPERATION_FAILURE = "{"
            + "\"success\":false,"
            + "\"message\":\"Operation failed\","
            + "\"location\":null,"
            + "\"token\":\"%s\""
            + "}";

    private static final String IMAGE_POST_REQUEST_TEMPLATE = "{" +
            "  \"vinyl_uuid\": \"REPLACE_VINYL_UUID\"," +
            "  \"filename\": \"REPLACE_FILENAME\"," +
            "  \"file\": \"REPLACE_FILE\"" +
            "}";

    private static final String IMAGE_GET_REQUEST_TEMPLATE = "{" +
            "  \"vinyl_uuid\": \"REPLACE_VINYL_UUID\","+
            "}";

    @Before
    public void setUp() throws Exception {
        add_vinyl(vinyl_id_1,"Prince", "Purple_Rain",
                1984, "/assets/Purple_Rain.jpg", "/assets/Purple_Rain_Back.jpg");
        add_vinyl(vinyl_id_2, "Finneas", "Blood_Harmony",
                2020,"/assets/Blood_Harmony.jpg", "/assets/Blood_Harmony_Back.jpg");
    }

    public void add_vinyl(String vinyl_id, String artist_name, String album_title, int release_year,
                          String image_loc_1, String image_loc_2){
        String artist_id = UUID.randomUUID().toString();
        String image_id_1 = UUID.randomUUID().toString();
        String image_id_2 = UUID.randomUUID().toString();

        jdbcTemplate
                .update("insert into vinyls.vinyls(vinyl_id) values (?)",
                        vinyl_id);
        jdbcTemplate
                .update("insert into vinyls.artists(artist_id, artist_name) values (?, ?)",
                        artist_id,artist_name);
        jdbcTemplate
                .update("insert into vinyls.albums(vinyl_id, album_title, release_year, artist_id) values (?,?,?,?)",
                        vinyl_id,album_title,release_year,artist_id);
        jdbcTemplate
                .update("insert into vinyls.images(image_id,image_loc,vinyl_id ) values (?,?,?)",
                        image_id_1, image_loc_1, vinyl_id);
        jdbcTemplate
                .update("insert into vinyls.images(image_id,image_loc,vinyl_id ) values (?,?,?)",
                        image_id_2, image_loc_2, vinyl_id);
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
        for(File file:imageFiles){
            file.delete();
        }
    }

    @Test
    @DirtiesContext
    public void postRequestShouldReturnDefaultMessage() throws Exception {
        String fileName = "Hotel_California_Back";
        File imageFile = new File("files/input/"+fileName+".jpeg");
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);
        String imageDetails = IMAGE_DETAILS
                .replaceAll("REPLACE_ASSET_LOCATION","/assets/"+fileName+".jpeg");

        imageMock.expectedBodiesReceived(imageDetails);
        template.setDefaultEndpointUri("direct:postImageEndpoint");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Filename",fileName);
        headers.put("vinyl_id",vinyl_id_1);

        String imageRequest = IMAGE_POST_REQUEST_TEMPLATE
                .replaceAll("REPLACE_VINYL_UUID",vinyl_id_1)
                .replaceAll("REPLACE_FILENAME",fileName)
                .replaceAll("REPLACE_FILE",  Base64.encodeBase64String(bytes));

        template.sendBody(imageRequest.replaceAll("\n","\\\\n"));
        imageMock.assertIsSatisfied();

        Thread.sleep(10_000L);
        File storedFile = new File("target/"+fileName+".jpeg");
        assertTrue(storedFile.exists());
        assertTrue(FileUtils.contentEquals(storedFile, imageFile));

        List<Map<String, Object>> imageInfo = jdbcTemplate
                .queryForList("select vinyl_id, image_loc from vinyls.images");

        String[] vinylIds = {vinyl_id_1,vinyl_id_1,vinyl_id_2,vinyl_id_2,vinyl_id_1};
        String[] imageLocs = {"/assets/Purple_Rain.jpg", "/assets/Purple_Rain_Back.jpg",
                "/assets/Blood_Harmony.jpg", "/assets/Blood_Harmony_Back.jpg","/assets/Hotel_California_Back.jpeg"};

        for(int i =0; i< vinylIds.length; i++){
            assertThat(imageInfo.get(i))
                    .extracting("Vinyl_Id")
                    .isEqualTo(vinylIds[i]);

            assertThat(imageInfo.get(i))
                    .extracting("Image_Loc")
                    .isEqualTo(imageLocs[i]);
        }
    }

    @Test
    @DirtiesContext
    public void shouldSaveWithCorrectExtension() throws Exception {
        String fileName = "Blood_Harmony.png";
        File imageFile = new File("files/input/"+fileName);
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);
        String imageDetails = IMAGE_DETAILS.replaceAll("REPLACE_ASSET_LOCATION","/assets/"+fileName);

        imageMock.expectedBodiesReceived(imageDetails);
        template.setDefaultEndpointUri("direct:postImageEndpoint");

        String imageRequest = IMAGE_POST_REQUEST_TEMPLATE
                .replaceAll("REPLACE_VINYL_UUID",vinyl_id_1)
                .replaceAll("REPLACE_FILENAME",fileName)
                .replaceAll("REPLACE_FILE",  Base64.encodeBase64String(bytes));

        template.sendBody(imageRequest.replaceAll("\n","\\\\n"));
        imageMock.assertIsSatisfied();

        Thread.sleep(10_000L);
        File storedFile = new File("target/"+fileName);
        assertTrue(storedFile.exists());
        assertTrue(FileUtils.contentEquals(storedFile, imageFile));
    }

    @Test
    @DirtiesContext
    public void shouldNotSaveWithIncorrectExtension() throws Exception {
        String fileName = "Blood_Harmony.ttt";
        File imageFile = new File("files/input/"+fileName);
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);

        imageMock.expectedBodiesReceived(OPERATION_FAILURE);
        template.setDefaultEndpointUri("direct:postImageEndpoint");

        String imageRequest = IMAGE_POST_REQUEST_TEMPLATE
                .replaceAll("REPLACE_VINYL_UUID",vinyl_id_1)
                .replaceAll("REPLACE_FILENAME",fileName)
                .replaceAll("REPLACE_FILE",  Base64.encodeBase64String(bytes));

        template.sendBody(imageRequest.replaceAll("\n","\\\\n"));
        imageMock.assertIsSatisfied();

        List<Map<String, Object>> imageInfo = jdbcTemplate
                .queryForList("select vinyl_id, image_loc from vinyls.images");

        String[] vinylIds = {vinyl_id_1,vinyl_id_1,vinyl_id_2,vinyl_id_2};
        String[] imageLocs = {"/assets/Purple_Rain.jpg", "/assets/Purple_Rain_Back.jpg",
                "/assets/Blood_Harmony.jpg", "/assets/Blood_Harmony_Back.jpg"};

        for(int i =0; i< vinylIds.length; i++){
            assertThat(imageInfo.get(i))
                    .extracting("Vinyl_Id")
                    .isEqualTo(vinylIds[i]);

            assertThat(imageInfo.get(i))
                    .extracting("Image_Loc")
                    .isEqualTo(imageLocs[i]);
        }
    }
}
