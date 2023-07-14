package org.janus.generate.props;

import org.janus.config.model.ConfigJanus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertyGenerator {

    public static void generateProperty(String path, ConfigJanus configJanus) {
        Properties props = new Properties();
        props.setProperty("spring.application.name", configJanus.getAppName());
        props.setProperty("spring.datasource.url", configJanus.getDatabaseUrl());
        props.setProperty("spring.datasource.username", configJanus.getDatabaseUser());
        props.setProperty("spring.datasource.password", configJanus.getDatabasePassword());
        props.setProperty("spring.datasource.testWhileIdle", "true");
        props.setProperty("spring.datasource.validationQuery", "SELECT 1");
        props.setProperty("spring.jpa.show-sql", "true");
        props.setProperty("spring.jpa.open-in-view", "true");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "none");
        props.setProperty("spring.jpa.properties.jakarta.persistence.validation.mode", "none");
        props.setProperty("spring.jpa.hibernate.naming-strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        props.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.setProperty("spring.jpa.properties.hibernate.jdbc.time_zone", "UTC");
        props.setProperty("spring.main.allow-circular-references", "true");
        props.setProperty("server.port", "5001");
        props.setProperty("apiprefix.v1", "/api/v1");
        props.setProperty("logging.level.root", "WARN");
        props.setProperty("logging.level." + configJanus.getRootPackage(), "INFO");
        props.setProperty("spring.jackson.default-property-inclusion", "non_null");
//        props.setProperty("", "");


        try {
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdir();
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path + "/application.properties"), StandardCharsets.UTF_8);
            props.store(outputStreamWriter, configJanus.getAppName() + "Spring Boot Configuration Properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
