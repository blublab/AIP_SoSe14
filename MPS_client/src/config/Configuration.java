package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private final String propertyFile = "./src/config/config.properties";

    private Properties properties = null;

    public Configuration(){
        this.properties = new Properties();
        try {
            this.properties.load(new FileInputStream(this.propertyFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key){
        return this.properties.getProperty(key);
    }
}
