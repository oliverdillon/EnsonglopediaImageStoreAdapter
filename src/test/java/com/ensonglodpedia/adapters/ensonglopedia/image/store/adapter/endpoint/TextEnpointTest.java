package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration
public class TextEnpointTest {

    @EndpointInject("mock:text")
    protected MockEndpoint ping;

    @Autowired
    ProducerTemplate template;

    private static final String OPERATION_SUCCEEDED = "{"
            + "\"success\": true,"
            + "\"message\": \"Operation succeeded.\""
            + "\"token\": \"%s\""
            + "}";


    @BeforeEach
    public void clearDirectories(){
        File textDirectory = new File("files/text");
        File[] textFiles = textDirectory.listFiles();
        for(File file:textFiles){
            file.delete();
        }
    }

    @Test
    @DirtiesContext
    public void shouldReturnDefaultMessage() throws Exception {
        String fileName = "Test_File";
        File imageFile = new File("files/input/"+fileName+".txt");
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);

        ping.expectedBodiesReceived(OPERATION_SUCCEEDED);
        template.setDefaultEndpointUri("direct:textEndpoint");
        template.sendBodyAndHeader(bytes,"Filename",fileName);
        ping.assertIsSatisfied();

        Thread.sleep(10_000L);
        File storedFile = new File("files/text/"+fileName+".txt");
        assertTrue(storedFile.exists());
    }
}
