
package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils;

import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
public class IntegrationConfig extends CamelConfiguration {

   @Inject
   private DataSource dataSource;

   @Bean
   public SqlComponent sql() {
      SqlComponent sqlComponent = new SqlComponent();
      sqlComponent.setDataSource(dataSource);
      return sqlComponent;
   }
}
