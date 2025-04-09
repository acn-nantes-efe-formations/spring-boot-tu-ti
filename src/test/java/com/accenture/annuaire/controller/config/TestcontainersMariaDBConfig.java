package com.accenture.annuaire.controller.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MariaDBContainer;

public class TestcontainersMariaDBConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final MariaDBContainer<?> MARIA_DB_CONTAINER =
            new MariaDBContainer<>("mariadb:11")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test")
            ;

    static {
        MARIA_DB_CONTAINER.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of(
                "spring.datasource.url=" + MARIA_DB_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + MARIA_DB_CONTAINER.getUsername(),
                "spring.datasource.password=" + MARIA_DB_CONTAINER.getPassword(),
                "spring.datasource.driver-class-name=org.mariadb.jdbc.Driver",
                "spring.jpa.hibernate.ddl-auto=create",
                "spring.jpa.defer-datasource-initialization=true",
                "spring.sql.init.mode=always",
                "spring.sql.init.schema-locations=classpath:test-containers-mariadb.sql",
                "spring.sql.init.data-locations=" // ← désactive `data.sql`
        ).applyTo(context.getEnvironment());
    }
}
