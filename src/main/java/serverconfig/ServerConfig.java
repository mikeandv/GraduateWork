package serverconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerConfig {

    private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private final File CONFIG = new File(rootPath + "application.properties");
    private static ServerConfig instance;
    private final Properties properties;

    private ServerConfig() {

        properties = new Properties();

        try {
            properties.load(new FileInputStream(CONFIG));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static ServerConfig getConfig() {
        if(instance == null) {
            instance = new ServerConfig();
        }
        return instance;
    }

    public String getParam(String key) {
        return properties.getProperty(key);
    }
}
