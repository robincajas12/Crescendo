package uce.project.com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import uce.project.com.condor.User;
import uce.project.com.database.AppDataBase;
import uce.project.com.cat.Cat;
import uce.project.com.cat.utils.JSON;
import uce.project.com.robin.ConfigReader;
import uce.project.com.robin.ai.GoogleAIBase;
import uce.project.com.robin.ai.TextToSong;

import java.awt.*;
import java.sql.DriverManager;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/**
 * Breve descripción de la clase.
 * Más detalles opcionales.
 */
@SpringBootApplication
public class Main {
    static AppDataBase db;
    public static AppDataBase getDb()
    {
        if(db == null) try {
            throw new Exception("Error please build the database");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return db;
    }
    static {
        ConfigReader.load("application.properties");
        try {
            db = Cat.buildDataBase(AppDataBase.class, DriverManager.getConnection(ConfigReader.get("db.connection_url")),true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        new SpringApplicationBuilder(Main.class)
                .run(args);
        db.userDao().insertUser(User.builder().username("xd").build());
        System.out.println(db.userDao().getById(1).stream().map(JSON::stringifyEntity).toList());
    }
}