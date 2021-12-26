package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.legacy.LocalVinylsRoute;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.IntegrationConfig;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.TestIntegration;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestIntegration.class,
        IntegrationConfig.class, LocalVinylsRoute.class})
@SpringBootTest
@ActiveProfiles("test")
public class LegacyVinylsRouteTest {

    @EndpointInject("mock:legacyVinyls")
    protected MockEndpoint legacyVinyls;

    @Autowired
    ProducerTemplate template;

    private final String OPERATION_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded.\","
            + "\"token\": \"%s\""
            + "}";

    private String vinyl_id_1 = UUID.randomUUID().toString();
    private String vinyl_id_2 = UUID.randomUUID().toString();
    private String vinyl_id_3 = UUID.randomUUID().toString();
    private String vinyl_id_4 = UUID.randomUUID().toString();
    private String vinyl_id_5 = UUID.randomUUID().toString();
    private String vinyl_id_6 = UUID.randomUUID().toString();

    private String initialContent = "{\n" +
            "  \"data\":[\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_1+"\",\n" +
            "      \"artist_name\": \"\",\n" +
            "      \"album_title\": \"\",\n" +
            "      \"release_year\": \"\",\n" +
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

    private String addedContent = "{\n" +
            "  \"data\":[\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_1+"\",\n" +
            "      \"artist_name\": \"\",\n" +
            "      \"album_title\": \"\",\n" +
            "      \"release_year\": \"\",\n" +
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
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_6+"\",\n" +
            "      \"artist_name\": \"Hello\",\n" +
            "      \"album_title\": \"example album_title\",\n" +
            "      \"release_year\": \"2020\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Add_Record_Button.jpg\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private String editedContent = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_1+"\",\n" +
            "      \"artist_name\": \"Hello\",\n" +
            "      \"album_title\": \"example album_title\",\n" +
            "      \"release_year\": \"2020\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Add_Record_Button.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_2+"\",\n" +
            "      \"artist_name\": \"Prince\",\n" +
            "      \"album_title\": \"Purple Rain\",\n" +
            "      \"release_year\": \"1984\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Purple_Rain.jpg\",\n" +
            "        \"/assets/Purple_Rain_Back.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_3+"\",\n" +
            "      \"artist_name\": \"Finneas\",\n" +
            "      \"album_title\": \"Blood Harmony\",\n" +
            "      \"release_year\": \"2019\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Blood_Harmony.png\",\n" +
            "        \"/assets/Blood_Harmony_Back.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_4+"\",\n" +
            "      \"artist_name\": \"Eagles\",\n" +
            "      \"album_title\": \"Hotel California\",\n" +
            "      \"release_year\": \"1976\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Hotel_California.jpg\",\n" +
            "        \"/assets/Hotel_California_Back.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \""+vinyl_id_5+"\",\n" +
            "      \"artist_name\": \"Eagles\",\n" +
            "      \"album_title\": \"Eagles\",\n" +
            "      \"release_year\": \"1972\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Eagles.jpeg\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Before
    public void resetJsonData () throws IOException, InterruptedException {
        String fileName = "data.json";
        File jsonFile = new File("files/input/"+fileName);
        FileUtils.writeStringToFile(jsonFile, initialContent);
        Thread.sleep(1_000L);
        System.out.println("resetting json");
    }

    @Test
    @DirtiesContext
    public void getVinylsShouldReturnDefaultMessage() throws Exception {
        initialContent = initialContent
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        template.setDefaultEndpointUri("direct:getVinylsLegacyEndpoint");
        template.sendBody("");
        legacyVinyls.expectedBodiesReceived(initialContent);
        legacyVinyls.assertIsSatisfied();
    }

    @Test
    @DirtiesContext
    public void postVinylsShouldReturnDefaultMessage() throws Exception {
        String body = "{\n" +
                "      \"vinyl_id\": \""+vinyl_id_6+"\",\n" +
                "      \"artist_name\": \"Hello\",\n" +
                "      \"album_title\": \"example album_title\",\n" +
                "      \"release_year\": \"2020\",\n" +
                "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
                "    }";

        body = body
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        template.setDefaultEndpointUri("direct:postVinylsLegacyEndpoint");
        template.sendBody(body);
        legacyVinyls.expectedBodiesReceived(OPERATION_SUCCEEDED);
        legacyVinyls.assertIsSatisfied();

        String fileName = "data.json";
        File jsonFile = new File("files/input/"+fileName);
        String storedjson = FileUtils.readFileToString(jsonFile);

        addedContent = addedContent
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        assertEquals(addedContent,storedjson);
    }

    @Test
    @DirtiesContext
    public void postVinylsEditExistingValues() throws Exception {
        String body = "{\n" +
                "      \"vinyl_id\": \""+vinyl_id_1+"\",\n" +
                "      \"artist_name\": \"Hello\",\n" +
                "      \"album_title\": \"example album_title\",\n" +
                "      \"release_year\": \"2020\",\n" +
                "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
                "    }";

        body = body
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        template.setDefaultEndpointUri("direct:postVinylsLegacyEndpoint");
        template.sendBody(body);
        legacyVinyls.expectedBodiesReceived(OPERATION_SUCCEEDED);
        legacyVinyls.assertIsSatisfied();

        String fileName = "data.json";
        File jsonFile = new File("files/input/"+fileName);
        String storedjson = FileUtils.readFileToString(jsonFile);

        editedContent = editedContent
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        assertEquals(editedContent,storedjson);
    }
}
