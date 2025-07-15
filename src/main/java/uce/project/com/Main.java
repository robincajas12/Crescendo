package uce.project.com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import uce.project.com.database.AppDataBase;
import uce.project.com.cat.Cat;
import uce.project.com.utils.ConfigReader;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase principal de la aplicación Spring Boot.
 * Esta clase inicializa la base de datos y arranca la aplicación Spring Boot.
 */
@SpringBootApplication
public class Main {
    /**
     * Instancia de la base de datos de la aplicación.
     */
    public static AppDataBase db;

    static {
        // Carga la configuración desde el archivo application.properties
        ConfigReader.load("application.properties");
        try {
            // Construye la base de datos utilizando Cat y la conexión JDBC
            db = Cat.buildDataBase(AppDataBase.class, DriverManager.getConnection(ConfigReader.get("db.connection_url")),true);
        } catch (SQLException e) {
            // En caso de error de SQL, lanza una RuntimeException
            throw new RuntimeException(e);
        }
    }

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        // Inicia la aplicación Spring Boot
        new SpringApplicationBuilder(Main.class)
                .run(args);
    }
}