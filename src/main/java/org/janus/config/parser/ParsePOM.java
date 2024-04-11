package org.janus.config.parser;

import org.apache.commons.io.IOUtils;
import org.janus.config.model.ConfigJanus;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ParsePOM {

    public static void runParser(ConfigJanus configJanus, String folderRoot) {
        try {
            FileInputStream fis = new FileInputStream(Objects.requireNonNull(ParseConfig.class.getClassLoader().getResource("pom.xml.model")).getFile());
            String contentPOM = IOUtils.toString(fis, StandardCharsets.UTF_8);
            contentPOM = contentPOM.replace("#appName#", configJanus.getAppName())
                    .replace("#rootPackage#", configJanus.getRootPackage())
                    .replace("#appId#", configJanus.getAppId());
            BufferedWriter writer = new BufferedWriter(new FileWriter(folderRoot + "/pom.xml"));
            writer.write(contentPOM);

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
