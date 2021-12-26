package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.vinyls.GetVinylsRoute;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.IntegrationConfig;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.TestIntegration;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.UUID;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestIntegration.class,
        IntegrationConfig.class, GetVinylsRoute.class })
@ActiveProfiles("test")
public class GetVinylRouteTest {

   private String local_vinyl_id_1 = UUID.randomUUID().toString();
   private String local_vinyl_id_2 = UUID.randomUUID().toString();
   private String json = "[\n" +
           "  {\n" +
           "    \"vinyl_id\": \""+local_vinyl_id_1+"\",\n" +
           "    \"artist_name\": \"Prince\",\n" +
           "    \"album_title\": \"Purple_Rain\",\n" +
           "    \"release_year\": \"1984\",\n" +
           "    \"imgs\": [" +
           "      \"/assets/Purple_Rain.jpg\"," +
           "      \"/assets/Purple_Rain_Back.jpg\"" +
           "      ]\n" +
           "  },\n" +
           "  {\n" +
           "    \"vinyl_id\": \""+local_vinyl_id_2+"\",\n" +
           "    \"artist_name\": \"Finneas\",\n" +
           "    \"album_title\": \"Blood_Harmony\",\n" +
           "    \"release_year\": \"2020\",\n" +
           "    \"imgs\":  [" +
           "      \"/assets/Blood_Harmony.jpg\"," +
           "      \"/assets/Blood_Harmony_Back.jpg\"" +
           "      ]\n" +
           "  }\n" +
           "]";

   @EndpointInject("mock:vinyls")
   protected MockEndpoint vinyl;

   @Autowired
   ProducerTemplate template;

   @Inject
   private JdbcTemplate jdbcTemplate;


   @Before
   public void setUp() throws Exception {
      add_vinyl(local_vinyl_id_1,"Prince", "Purple_Rain",
              1984, "/assets/Purple_Rain.jpg", "/assets/Purple_Rain_Back.jpg");
      add_vinyl(local_vinyl_id_2, "Finneas", "Blood_Harmony",
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

   @Test
   @DirtiesContext
   public void testGetVinylRouteSuccess() throws Exception {
      json = json
              .replaceAll(" ","")
              .replaceAll("[\t\r\n]","")
              .replaceAll("(?<=[:,\"{}\\[\\]])\\s+","");
      vinyl.expectedBodiesReceived(json);
      template.sendBody("direct:getVinylsEndpoint",null);
      vinyl.assertIsSatisfied();
   }


}
