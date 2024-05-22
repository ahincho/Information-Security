package com.unsa.information.security.main;

import com.unsa.information.security.configuration.ConfigurationLoader;

public class Application {
    private static ConfigurationLoader configurationLoader;
    public static void main(String[] args) {
        setUp();
    }
    public static void setUp() {
        configurationLoader = ConfigurationLoader.getConfigurationLoaderInstance();
        System.out.format("Application name: %s", configurationLoader.getProperty("application.name"));
        System.out.format("Application version: %s", configurationLoader.getProperty("application.version"));
    }
}