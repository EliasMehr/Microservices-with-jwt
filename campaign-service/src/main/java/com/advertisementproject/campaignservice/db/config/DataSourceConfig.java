package com.advertisementproject.campaignservice.db.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the data source, controlled by environment variables.
 */
@Configuration
public class DataSourceConfig {

    /**
     * Username for the database connection, set through environment variable.
     */
    private final String USERNAME = System.getenv("POSTGRES_USER");
    /**
     * Password for the database connection, set through environment variable.
     */
    private final String PASSWORD = System.getenv("POSTGRES_PASSWORD");
    /**
     * Database connection URL, which is set to postgresql but host and database is set through environment variables.
     */
    private final String URL = "jdbc:postgresql://" + System.getenv("POSTGRES_HOST") + ":5432/" + System.getenv("POSTGRES_DB");

    /**
     * HikariDataSource configuration bean.
     *
     * @return a configured hikari data source for connecting to a database.
     */
    @Bean
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder.create()
                .username(USERNAME)
                .password(PASSWORD)
                .url(URL)
                .type(HikariDataSource.class)
                .build();
    }
}
