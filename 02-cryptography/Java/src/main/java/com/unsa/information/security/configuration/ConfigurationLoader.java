package com.unsa.information.security.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {
    private static ConfigurationLoader configurationLoaderInstance;
    private final Properties applicationProperties;
    private ConfigurationLoader() {
        applicationProperties = new Properties();
        loadProperties();
    }
    public static synchronized ConfigurationLoader getConfigurationLoaderInstance() {
        if (configurationLoaderInstance == null) {
            configurationLoaderInstance = new ConfigurationLoader();
        }
        return configurationLoaderInstance;
    }
    private void loadProperties() {
        String profile = System.getProperty("profile", "development");
        String configurationFile = String.format("application-%s.properties", profile);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configurationFile)) {
            if (inputStream == null) {
                System.out.format("Can not load %s file\n", configurationFile);
                return;
            }
            applicationProperties.load(inputStream);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    public String getProperty(String property) {
        return applicationProperties.getProperty(property);
    }
}