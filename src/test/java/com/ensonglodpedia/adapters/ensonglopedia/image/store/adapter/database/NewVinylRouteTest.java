package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.database;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.LocalVinylsRoute;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.IntegrationConfig;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Test case for testing the execution of the SQL component-based route for
 * routing orders from the orders database to a log component.
 * 
 * @author Michael Hoffman, Pluralsight
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestIntegration.class,
      IntegrationConfig.class, LocalVinylsRoute.class })
public class NewVinylRouteTest {

   @Inject
   private JdbcTemplate jdbcTemplate;

   /**
    * Set up test fixture
    * 
    * @throws Exception
    */
//   @Before
//   public void setUp() throws Exception {
//      // Insert catalog and customer data
//      jdbcTemplate
//            .execute("insert into orders.catalogitem (id, itemnumber, itemname, itemtype) "
//                  + "values (1, '078-1344200444', 'Build Your Own JavaScript Framework in Just 24 Hours', 'Book')");
//      jdbcTemplate
//            .execute("insert into orders.customer (id, firstname, lastname, email) "
//                  + "values (1, 'Larry', 'Horse', 'larry@hello.com')");
//   }

   @After
   public void tearDown() throws Exception {
      jdbcTemplate.execute("delete from vinyls.vinyls");
      jdbcTemplate.execute("delete from vinyls.artists");
      jdbcTemplate.execute("delete from vinyls.albums");
      jdbcTemplate.execute("delete from vinyls.songs");
      jdbcTemplate.execute("delete from vinyls.images");
   }

   @Test
   public void testVinylRouteSuccess() throws Exception {
      jdbcTemplate
            .execute("insert into vinyls.vinyls (id) values (1)");
      Thread.sleep(7000);
      int id = jdbcTemplate
              .queryForObject("select id from vinyls.vinyls",Integer.class);
      assertEquals(1,id);
   }
}
