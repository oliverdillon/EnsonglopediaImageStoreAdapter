package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ContextConfiguration
public class VinylsEndpointTest {

    @EndpointInject("mock:legacyVinyls")
    protected MockEndpoint ping;

    @Autowired
    ProducerTemplate template;

    private String initialContent = "{\n" +
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

    private String addedContent = "{\n" +
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
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"artist\": \"Hello\",\n" +
            "      \"album\": \"example album\",\n" +
            "      \"date\": \"2020\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Add_Record_Button.jpg\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private String editedContent = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": 0,\n" +
            "      \"artist\": \"Hello\",\n" +
            "      \"album\": \"example album\",\n" +
            "      \"date\": \"2020\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Add_Record_Button.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"artist\": \"Prince\",\n" +
            "      \"album\": \"Purple Rain\",\n" +
            "      \"date\": \"1984\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Purple_Rain.jpg\",\n" +
            "        \"/assets/Purple_Rain_Back.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"artist\": \"Finneas\",\n" +
            "      \"album\": \"Blood Harmony\",\n" +
            "      \"date\": \"2019\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Blood_Harmony.png\",\n" +
            "        \"/assets/Blood_Harmony_Back.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"artist\": \"Eagles\",\n" +
            "      \"album\": \"Hotel California\",\n" +
            "      \"date\": \"1976\",\n" +
            "      \"imgs\": [\n" +
            "        \"/assets/Hotel_California.jpg\",\n" +
            "        \"/assets/Hotel_California_Back.jpg\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"artist\": \"Eagles\",\n" +
            "      \"album\": \"Eagles\",\n" +
            "      \"date\": \"1972\",\n" +
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
                "      \"id\": 5,\n" +
                "      \"artist\": \"Hello\",\n" +
                "      \"album\": \"example album\",\n" +
                "      \"date\": \"2020\",\n" +
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
                "      \"id\": 0,\n" +
                "      \"artist\": \"Hello\",\n" +
                "      \"album\": \"example album\",\n" +
                "      \"date\": \"2020\",\n" +
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

        assertEquals(storedjson, editedContent);
    }
}
