import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {
    private static final String configurationFile = "application.properties";
    private static ConfigurationLoader configurationLoaderInstance;
    private final Properties properties;
    private ConfigurationLoader() {
        properties = new Properties();
        loadProperties();
    }
    public static synchronized ConfigurationLoader getConfigurationLoaderInstance() {
        if (configurationLoaderInstance == null) {
            configurationLoaderInstance = new ConfigurationLoader();
        }
        return configurationLoaderInstance;
    }
    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configurationFile)) {
            if (input == null) {
                System.out.println("Can not load configuration file");
                return;
            }
            properties.load(input);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}