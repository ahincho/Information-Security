public class Application {
    public static void main(String[] args) {
        ConfigurationLoader configLoader = ConfigurationLoader.getConfigurationLoaderInstance();
        System.out.println("Application name: " + configLoader.getProperty("application.name"));
        System.out.println("Application version: " + configLoader.getProperty("application.version"));
        System.out.println("Dictionary archive: " + configLoader.getProperty("application.archive.dictionary"));
    }
}