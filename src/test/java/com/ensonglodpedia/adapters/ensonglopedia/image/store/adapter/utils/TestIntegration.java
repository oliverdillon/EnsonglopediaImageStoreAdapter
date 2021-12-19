package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Spring configuration class used for test execution. Sets up beans to support
 * unit testing.
 * 
 * @author Michael Hoffman, Pluralsight
 * 
 */
@Configuration
@Profile("test")
public class TestIntegration {

   private static final String CREATE = "create";
   private static final String DESTROY = "destroy";

   @Inject
   private Environment environment;

   /**
    * DataSource bean for the Apache Derby database.
    * 
    * @return
    */
   @Bean
   public DataSource dataSource() {
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(EmbeddedDriver.class.getName());
      dataSource.setUrl("jdbc:derby:memory:vinyls;create=true");
      return dataSource;
   }

   /**
    * Spring JDBC Template used for querying the Derby database.
    * 
    * @return
    */
   @Bean
   public JdbcTemplate jdbcTemplate() {
      JdbcTemplate jdbc = new JdbcTemplate(dataSource());
      return jdbc;
   }

   /**
    * Derby database bean used for creating and destroying the derby database as
    * part of the Spring container lifecycle. Note that the bean annotation sets
    * initMethod equal to the DerbyDatabaseBean method create and sets
    * destroyMethod to the DerbyDatabaseBean method destroy.
    * 
    * @return
    */
   @Bean(initMethod = CREATE, destroyMethod = DESTROY)
   public DerbyDatabaseBean derbyDatabaseBean() {
      DerbyDatabaseBean derby = new DerbyDatabaseBean();
      derby.setJdbcTemplate(jdbcTemplate());
      return derby;
   }

}
