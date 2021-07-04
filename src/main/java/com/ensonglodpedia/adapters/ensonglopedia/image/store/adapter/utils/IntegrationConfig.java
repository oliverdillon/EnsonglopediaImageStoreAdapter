
package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.ImageRoute;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.PingRoute;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.local.LocalVinylsRoute;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.WebserverRoute;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.vinyls.GetVinylsRoute;
import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.vinyls.PostVinylsRoute;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan("com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes")
public class IntegrationConfig extends CamelConfiguration {

   @Inject
   private Environment environment;

   @Inject
   private DataSource dataSource;

   @Bean
   public SqlComponent sql() {
      SqlComponent sqlComponent = new SqlComponent();
      sqlComponent.setDataSource(dataSource);
      return sqlComponent;
   }

   @Autowired
   private ImageRoute imageRoute;

   @Autowired
   private PingRoute pingRoute;

   @Autowired
   private LocalVinylsRoute localVinylsRoute;

   @Autowired
   private GetVinylsRoute getVinylsRoute;

   @Autowired
   private PostVinylsRoute postVinylsRoute;

   @Autowired
   private WebserverRoute webserverRoute;

   @Override
   public List<RouteBuilder> routes() {
      return Arrays.asList(imageRoute, pingRoute,
              localVinylsRoute, getVinylsRoute,
              postVinylsRoute,webserverRoute);
   }
}
