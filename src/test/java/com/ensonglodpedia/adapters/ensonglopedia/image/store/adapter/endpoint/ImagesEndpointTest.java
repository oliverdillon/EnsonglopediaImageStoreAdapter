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
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration
public class ImagesEndpointTest {

    @EndpointInject("mock:images")
    protected MockEndpoint imageMock;

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
    public void postRequestShouldReturnDefaultMessage() throws Exception {
        String fileName = "Hotel_California_Back";
        File imageFile = new File("files/input/"+fileName+".jpeg");
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);

        imageMock.expectedBodiesReceived(OPERATION_SUCCEEDED);
        template.setDefaultEndpointUri("direct:postImageEndpoint");
        template.sendBodyAndHeader(bytes,"Filename",fileName);
        imageMock.assertIsSatisfied();

        Thread.sleep(10_000L);
        File storedFile = new File("files/images/"+fileName+".jpeg");
        assertTrue(storedFile.exists());
    }

    @Test
    @DirtiesContext
    public void getRequestShouldReturnImage() throws Exception {
        String fileName = "Hotel_California_Back.jpeg";
        File imageFile = new File("files/input/"+fileName);
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);
        File storedFile = new File("files/images/"+fileName);
        FileUtils.writeByteArrayToFile(storedFile, bytes);
        Thread.sleep(10_000L);
        assertTrue(storedFile.exists());

        imageMock.expectedBodiesReceived(bytes);
        template.setDefaultEndpointUri("direct:getImageEndpoint");
        template.sendBodyAndHeader(bytes,"Filename",fileName);
        imageMock.assertIsSatisfied();

    }

    @Test
    @DirtiesContext
    public void shouldSaveWithCorrectExtension() throws Exception {
        String fileName = "Blood_Harmony.png";
        File imageFile = new File("files/input/"+fileName);
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);

        imageMock.expectedBodiesReceived(OPERATION_SUCCEEDED);
        template.setDefaultEndpointUri("direct:postImageEndpoint");
        template.sendBodyAndHeader(bytes,"Filename",fileName);
        imageMock.assertIsSatisfied();

        Thread.sleep(10_000L);
        File storedFile = new File("files/images/"+fileName);
        assertTrue(storedFile.exists());
    }

    @Test
    @DirtiesContext
    public void shouldNotSaveWithIncorrectExtension() throws Exception {
        String fileName = "Blood_Harmony.ttt";
        File imageFile = new File("files/input/"+fileName);
        byte[] bytes = FileUtils.readFileToByteArray(imageFile);

        imageMock.expectedBodiesReceived("{Unknown file extension}");
        template.setDefaultEndpointUri("direct:postImageEndpoint");
        template.sendBodyAndHeader(bytes,"Filename",fileName);
        imageMock.assertIsSatisfied();
    }
}
