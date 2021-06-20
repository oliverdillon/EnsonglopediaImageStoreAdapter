package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.sql.DataSource;
@Configuration
public class DataConfig {

   @Inject
   private Environment environment;

   @Bean
   public DataSource dataSource() {
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver"));
      dataSource.setUrl(environment.getProperty("spring.datasource.url"));
      dataSource.setUsername(environment.getProperty("spring.datasource.username"));
      dataSource.setPassword(environment.getProperty("spring.datasource.password"));
      return dataSource;
   }


}
