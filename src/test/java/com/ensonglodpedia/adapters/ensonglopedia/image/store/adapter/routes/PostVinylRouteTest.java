package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.vinyls.PostVinylsRoute;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestIntegration.class,
        IntegrationConfig.class, PostVinylsRoute.class })
@ActiveProfiles("test")
public class PostVinylRouteTest {

   private String local_vinyl_id_1 = UUID.randomUUID().toString();
   private String local_vinyl_id_2 = UUID.randomUUID().toString();
   private String json = "[\n" +
           "  {\n" +
           "    \"VINYL_ID\": \""+local_vinyl_id_1+"\",\n" +
           "    \"ARTIST_NAME\": \"Prince\",\n" +
           "    \"ALBUM_TITLE\": \"Purple_Rain\",\n" +
           "    \"RELEASE_YEAR\": \"1984\",\n" +
           "    \"IMGS\": null\n" +
           "  },\n" +
           "  {\n" +
           "    \"VINYL_ID\": \""+local_vinyl_id_2+"\",\n" +
           "    \"ARTIST_NAME\": \"Finneas\",\n" +
           "    \"ALBUM_TITLE\": \"Blood_Harmony\",\n" +
           "    \"RELEASE_YEAR\": \"2020\",\n" +
           "    \"IMGS\": null\n" +
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
      add_vinyl(local_vinyl_id_1,"Prince", "Purple_Rain", 1984);
      add_vinyl(local_vinyl_id_2, "Finneas", "Blood_Harmony", 2020);
   }

   public void add_vinyl(String vinyl_id, String artist_name, String album_title, int release_year){
      String local_artist_id = UUID.randomUUID().toString();
      jdbcTemplate
              .update("insert into vinyls.vinyls(vinyl_id) values (?)",
                      vinyl_id);
      jdbcTemplate
              .update("insert into vinyls.artists(artist_id, artist_name) values (?, ?)",
                      local_artist_id,artist_name);
      jdbcTemplate
              .update("insert into vinyls.albums(vinyl_id, album_title, release_year, artist_id) values (?,?,?,?)",
                      vinyl_id,album_title,release_year,local_artist_id);
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
   public void testPostVinylRouteSuccess() throws Exception {

      template.setDefaultEndpointUri("direct:postVinylsEndpoint");

      Map<String,Object> headers =new HashMap<>();
      headers.put("Artist_Name","Hello");
      headers.put("Album_Title","Example");
      headers.put("Release_Year","2020");
      template.sendBodyAndHeaders(null,headers);
      List<Map<String, Object>> vinylInfo = jdbcTemplate
              .queryForList("select vinyl_id, artist_name, album_title, release_year from vinyls.albums" +
                      " inner join vinyls.artists on vinyls.albums.artist_id=vinyls.artists.artist_id");
      vinyl.expectedBodiesReceived(json);
      vinyl.assertIsSatisfied();

      String[] artistNames = {"Prince","Finneas","Hello"};
      String[] albumTitles = {"Purple_Rain","Blood_Harmony","Example"};
      String[] releaseYear = { "1984", "2020", "2020" };

      for(int i =0; i< releaseYear.length; i++){
         assertThat(vinylInfo.get(i))
                 .extracting("Artist_Name")
                 .isEqualTo(artistNames[i]);

         assertThat(vinylInfo.get(i))
                 .extracting("Album_Title")
                 .isEqualTo(albumTitles[i]);

         assertThat(vinylInfo.get(i))
                 .extracting("Release_Year")
                 .isEqualTo(releaseYear[i]);
      }
   }
}
