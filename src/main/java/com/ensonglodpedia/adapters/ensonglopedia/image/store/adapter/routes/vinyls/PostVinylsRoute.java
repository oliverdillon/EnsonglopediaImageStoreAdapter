package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.routes.vinyls;

import com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.processes.SqlBuilderProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.utils.ServiceConstants.OPERATION_SUCCEEDED;

@Component
public class PostVinylsRoute extends RouteBuilder {

//    @Autowired
//    public SqlComponent sql;

    String vinyl_uuid = UUID.randomUUID().toString();
    String artist_uuid = UUID.randomUUID().toString();

    @Override
    public void configure() throws Exception {
        from("direct:postVinylsEndpoint")
                .process(new SqlBuilderProcessor())
//                .to("sql:select vinyls.add_vinyl('" +vinyl_uuid+"'," +
//                        "'" +artist_uuid+"'," +
//                        "'Prince'," +
//                        "'Purple Rain'," +
//                        "1984)")
                .setBody(constant(OPERATION_SUCCEEDED))
                .to("mock:mockVinylsEndpoint");
    }
}
