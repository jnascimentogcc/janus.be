package org.janus.config.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.janus.config.model.ConfigJanus;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class ParseConfig {

    public static ConfigJanus runParser() {
        try {
            File fileConfig = new File(Objects.requireNonNull(ParseConfig.class.getClassLoader().getResource("config.json")).toURI());
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(fileConfig, ConfigJanus.class);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
