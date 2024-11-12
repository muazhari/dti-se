package org.dti.se.module3session11.outers.datastores;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@Configuration
@EnableR2dbcRepositories(
        basePackages = "org.dti.se.module3session11.outers.repositories.ones",
        entityOperationsRef = "oneTemplate"
)
public class OneDatastore {

    @Autowired
    private Environment environment;

    @Bean
    public ConnectionFactory oneConnectionFactory() {
        String url = String.format(
                "r2dbc:postgresql://%s:%s@%s:%s/%s",
                environment.getProperty("datastore.one.user"),
                environment.getProperty("datastore.one.password"),
                environment.getProperty("datastore.one.host"),
                environment.getProperty("datastore.one.port"),
                environment.getProperty("datastore.one.database")
        );
        return ConnectionFactories
                .get(url);
    }


    @Bean
    public R2dbcEntityTemplate oneTemplate(@Qualifier("oneConnectionFactory") ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

}
