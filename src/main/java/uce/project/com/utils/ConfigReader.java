package uce.project.com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase de utilidad para leer propiedades de archivos de configuración.
 * Permite cargar un archivo de propiedades y acceder a sus valores por clave.
 */
public class ConfigReader {
    /**
     * Objeto Properties que almacena las propiedades cargadas.
     */
    private static final Properties prop = new Properties();

    /**
     * Carga las propiedades desde un archivo de recursos especificado.
     * @param resourceName El nombre del archivo de recursos (ej. "application.properties").
     */
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

    /**
     * Obtiene el valor de una propiedad dado su clave.
     * @param key La clave de la propiedad.
     * @return El valor de la propiedad como String, o `null` si la clave no se encuentra.
     */
    public static String get(String key) {
        return prop.getProperty(key);
    }
}
