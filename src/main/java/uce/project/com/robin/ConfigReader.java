package uce.project.com.robin;

import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties prop = new Properties();

    public static void load(String resourceName) {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (input == null) {
                System.err.println("Could not find resource: " + resourceName);
                return;
            }
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}
