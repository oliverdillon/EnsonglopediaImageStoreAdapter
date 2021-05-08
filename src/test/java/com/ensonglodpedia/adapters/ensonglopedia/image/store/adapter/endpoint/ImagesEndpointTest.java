package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.endpoint;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration
public class ImagesEndpointTest {

    @EndpointInject("mock:images")
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
        File imageDirectory = new File("files/images");
        File[] imageFiles = imageDirectory.listFiles();
        for(File file:imageFiles){
            file.delete();
        }
    }

    @Test
    @DirtiesContext
    public void shouldReturnDefaultMessage() throws Exception {
        String fileName = "Hotel_California_Back";
        File imageFile = new File("files/input/"+fileName+".jpeg");
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);

        ping.expectedBodiesReceived(OPERATION_SUCCEEDED);
        template.setDefaultEndpointUri("direct:imagesEndpoint");
        template.sendBodyAndHeader(bytes,"Filename",fileName);
        ping.assertIsSatisfied();

        Thread.sleep(10_000L);
        File storedFile = new File("files/images/"+fileName+".jpeg");
        assertTrue(storedFile.exists());
    }
}
