package org.dti.se.module3session9.outers.datastores;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;


@Configuration
public class OneDatastore {

    String filePath = "./src/main/resources/database";
    String r2dbcUrl = "r2dbc:h2:file:///" + filePath + ";LOCK_MODE=1;AUTO_SERVER=TRUE;";
    String jdbcUrl = "jdbc:h2:file:" + filePath + ";LOCK_MODE=1;AUTO_SERVER=TRUE;";

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(jdbcUrl, "", "")
                .locations("classpath:")
                .cleanDisabled(false)
                .load();

        flyway.clean();
        flyway.repair();

        return flyway;
    }


    @Bean(name = "oneConnectionFactory")
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories
                .get(r2dbcUrl);
    }


    @Bean(name = "oneDatabaseClient")
    public DatabaseClient databaseClient(@Qualifier("oneConnectionFactory") ConnectionFactory connectionFactory) {
        return DatabaseClient.create(connectionFactory);
    }

}
