package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.database;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.local.LocalVinylsRoute;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Test case for testing the execution of the SQL component-based route for
 * routing orders from the orders database to a log component.
 *
 * @author Michael Hoffman, Pluralsight
 *
 */
@SpringBootTest
@ContextConfiguration(classes = { TestIntegration.class, LocalVinylsRoute.class })
public class NewVinylRouteTest {

   String json = "[\n" +
           "  {\n" +
           "    \"imgs\": null,\n" +
           "    \"vinyl_id\": \"6e8f9e65-dc47-49ce-812d-46c27d106ce9\",\n" +
           "    \"artist_name\": \"Prince\",\n" +
           "    \"album_title\": \"Purple Rain\",\n" +
           "    \"year\": \"1984\"\n" +
           "  },\n" +
           "  {\n" +
           "    \"imgs\": null,\n" +
           "    \"vinyl_id\": \"005da771-7f72-4e3e-8ccf-d1753c17b57f\",\n" +
           "    \"artist_name\": \"Finneas\",\n" +
           "    \"album_title\": \"Blood Harmony\",\n" +
           "    \"year\": \"2020\"\n" +
           "  }\n" +
           "]";

   @EndpointInject("mock:mockVinylsEndpoint")
   protected MockEndpoint vinyl;

   @Autowired
   ProducerTemplate template;

   @Inject
   private JdbcTemplate jdbcTemplate;

   String local_vinyl_id = UUID.randomUUID().toString();

   String local_artist_id = UUID.randomUUID().toString();

   @Before
   public void setUp() throws Exception {
      add_vinyl("Prince", "Purple Rain", 1984);
      add_vinyl("Finneas", "Blood Harmony", 2020);
   }

   public void add_vinyl(String local_artist_name, String local_album_title, int local_release_year){
      // Insert catalog and customer data
      jdbcTemplate
              .execute("insert into vinyls.vinyls(vinyl_id) values ("+local_vinyl_id+");");
      jdbcTemplate
              .execute("insert into vinyls.artists(artist_id, artist_name) values ('"+local_artist_id+"', '"+local_artist_name+"');");
      jdbcTemplate
              .execute("insert into vinyls.albums(vinyl_id, year, artist_id, album_title) values ('"+local_vinyl_id+"', '"+local_release_year+"', '"+local_artist_id+"', '"+local_album_title+"');");
   }

   @After
   public void tearDown() throws Exception {
      jdbcTemplate.execute("delete from vinyls.vinyls");
      jdbcTemplate.execute("delete from vinyls.artists");
      jdbcTemplate.execute("delete from vinyls.albums");
      jdbcTemplate.execute("delete from vinyls.songs");
      jdbcTemplate.execute("delete from vinyls.images");
   }

   @Test
   @DirtiesContext
   public void testVinylRouteSuccess() throws Exception {
      vinyl.expectedBodiesReceived(json);
      template.sendBody("direct:postVinylsEndpoint",null);
      vinyl.assertIsSatisfied();
   }
}
