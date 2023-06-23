package org.janus;

import org.janus.command.Runner;
import org.janus.config.model.ConfigJanus;
import org.janus.config.parser.ParseConfig;

public class Application {

    public static void main(String[] args) {

        ConfigJanus configJanus = ParseConfig.runParser();
        Runner.execute(configJanus);
    }
}
