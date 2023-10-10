package com.lfin.assignment.common;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

@TestConfiguration
public class TestDataConfiguration {

    @Bean
    public MySQLContainer<?> mysqlContainer() {
        return TestMySQLContainer.getInstance();
    }

    @Bean
    @Primary
    public DataSource dataSource(MySQLContainer<?> mysqlContainer) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(mysqlContainer.getJdbcUrl());
        dataSourceBuilder.username(mysqlContainer.getUsername());
        dataSourceBuilder.password(mysqlContainer.getPassword());
        return dataSourceBuilder.build();
    }

}
