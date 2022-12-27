package fr.whitefox;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static Configuration instance = null;

    private Properties config = new Properties();

    private Configuration() {
        try (InputStream input = new FileInputStream("config.properties")) {
            config.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }

    public void setProperty(String key, String value) {
        config.setProperty(key, value);
        save();
    }

    public void save() {
        try (FileOutputStream fos = new FileOutputStream("config.properties")) {
            config.store(fos, "Configuration du bot de rank.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean exist(String key) {
        return key != null;
    }
}