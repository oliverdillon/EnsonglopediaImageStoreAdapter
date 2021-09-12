package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
public class LegacyVinylsEndpointTest {

    @EndpointInject("mock:legacyVinyls")
    protected MockEndpoint ping;

    @Autowired
    ProducerTemplate template;

    private String initialContent = "{\n" +
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

    private String addedContent = "{\n" +
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
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \"5\",\n" +
            "      \"artist_name\": \"Hello\",\n" +
            "      \"album_title\": \"example album_title\",\n" +
            "      \"year\": \"2020\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Add_Record_Button.jpg\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private String editedContent = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"vinyl_id\": \"0\",\n" +
            "      \"artist_name\": \"Hello\",\n" +
            "      \"album_title\": \"example album_title\",\n" +
            "      \"year\": \"2020\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Add_Record_Button.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \"1\",\n" +
            "      \"artist_name\": \"Prince\",\n" +
            "      \"album_title\": \"Purple Rain\",\n" +
            "      \"year\": \"1984\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Purple_Rain.jpg\",\n" +
            "        \"/assets/Purple_Rain_Back.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \"2\",\n" +
            "      \"artist_name\": \"Finneas\",\n" +
            "      \"album_title\": \"Blood Harmony\",\n" +
            "      \"year\": \"2019\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Blood_Harmony.png\",\n" +
            "        \"/assets/Blood_Harmony_Back.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \"3\",\n" +
            "      \"artist_name\": \"Eagles\",\n" +
            "      \"album_title\": \"Hotel California\",\n" +
            "      \"year\": \"1976\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Hotel_California.jpg\",\n" +
            "        \"/assets/Hotel_California_Back.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"vinyl_id\": \"4\",\n" +
            "      \"artist_name\": \"Eagles\",\n" +
            "      \"album_title\": \"Eagles\",\n" +
            "      \"year\": \"1972\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Eagles.jpeg\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @BeforeEach
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

        ping.expectedBodiesReceived(initialContent);
        template.setDefaultEndpointUri("direct:getVinylsLegacyEndpoint");
        template.sendBody("");
        ping.assertIsSatisfied();
    }

    @Test
    @DirtiesContext
    public void postVinylsShouldReturnDefaultMessage() throws Exception {
        String body = "{\n" +
                "      \"vinyl_id\": \"5\",\n" +
                "      \"artist_name\": \"Hello\",\n" +
                "      \"album_title\": \"example album_title\",\n" +
                "      \"year\": \"2020\",\n" +
                "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
                "    }";

        body = body
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        template.setDefaultEndpointUri("direct:postVinylsLegacyEndpoint");
        template.sendBody(body);
        ping.assertIsSatisfied();

        String fileName = "data.json";
        File jsonFile = new File("files/input/"+fileName);
        String storedjson = FileUtils.readFileToString(jsonFile);

        addedContent = addedContent
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        assertEquals(storedjson, addedContent);
    }

    @Test
    @DirtiesContext
    public void postVinylsEditExistingValues() throws Exception {
        String body = "{\n" +
                "      \"vinyl_id\": \"0\",\n" +
                "      \"artist_name\": \"Hello\",\n" +
                "      \"album_title\": \"example album_title\",\n" +
                "      \"year\": \"2020\",\n" +
                "      \"imgs\": [\"/assets/Add_Record_Button.jpg\"]\n" +
                "    }";

        body = body
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        template.setDefaultEndpointUri("direct:postVinylsLegacyEndpoint");
        template.sendBody(body);
        ping.assertIsSatisfied();

        String fileName = "data.json";
        File jsonFile = new File("files/input/"+fileName);
        String storedjson = FileUtils.readFileToString(jsonFile);

        editedContent = editedContent
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        assertEquals(editedContent,storedjson);
    }
}
