package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;

@SpringBootTest
@ContextConfiguration
public class VinylsEndpointTest {

    @EndpointInject("mock:vinyls")
    protected MockEndpoint ping;

    @Autowired
    ProducerTemplate template;

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

    @Test
    @DirtiesContext
    public void shouldReturnDefaultMessage() throws Exception {
        String fileName = "Test_File";
        File imageFile = new File("files/input/"+fileName+".txt");
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);

        expectedContent = expectedContent
                .replaceAll("[\t\r\n]","")
                .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");

        ping.expectedBodiesReceived(expectedContent);
        template.setDefaultEndpointUri("direct:getVinylsEndpoint");
        template.sendBodyAndHeader(bytes,"Filename",fileName);
        ping.assertIsSatisfied();
    }
}
