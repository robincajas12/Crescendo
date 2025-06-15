package uce.project.com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import uce.project.com.database.AppDataBase;
import uce.project.com.cat.Cat;
import uce.project.com.database.entities.Product;
import uce.project.com.database.entities.User;
import uce.project.com.cat.utils.JSON;

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

    static {
        try {
            db = Cat.buildDataBase(AppDataBase.class, DriverManager.getConnection("jdbc:mysql://localhost:3340/employees?user=root&password=root"),true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        new SpringApplicationBuilder(Main.class)
                .properties("server.port = 3000")
                .run(args);
        db.userDao().insertUser(User.builder().name("Hola").build());
        System.out.println(db.userDao().getAll().stream().map(JSON::stringifyEntity).toList());

    }
}