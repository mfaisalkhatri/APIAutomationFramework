package io.github.mfaisalkhatri.config;

import java.io.IOException;
import java.io.InputStream;

import io.github.mfaisalkhatri.config.model.Frameworkconfig;
import io.github.mfaisalkhatri.exceptions.ConfigurationException;
import tools.jackson.databind.ObjectMapper;

public class JsonConfigProvider implements ConfigProvider {

    private final ObjectMapper objectMapper;
    private final String       configFile;

    public JsonConfigProvider (final String configFile) {
        this.objectMapper = new ObjectMapper ();
        this.configFile = configFile;
    }

    @Override
    public Frameworkconfig load () {

        try (
            final InputStream inputStream = getClass ().getClassLoader ()
                .getResourceAsStream (this.configFile)) {

            if (inputStream == null) {
                throw new ConfigurationException ("Config file not found!" + this.configFile);
            }
            return this.objectMapper.readValue (inputStream, Frameworkconfig.class);
        } catch (final IOException e) {
            throw new ConfigurationException ("Failed to load config file" + this.configFile, e);
        }

    }

}
