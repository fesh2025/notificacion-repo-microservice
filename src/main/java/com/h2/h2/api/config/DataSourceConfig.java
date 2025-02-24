package com.h2.h2.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://faq-service-server.mysql.database.azure.com:3306/faq-service-database");
        dataSource.setUsername("vayemxvcqu");
        dataSource.setPassword("Hz$U8OwUuPYYACqa");
        return dataSource;
    }
}