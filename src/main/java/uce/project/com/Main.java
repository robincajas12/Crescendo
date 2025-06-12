package uce.project.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import uce.project.com.database.AppDataBase;
import uce.project.com.cat.Cat;
import uce.project.com.database.entities.Product;
import uce.project.com.database.entities.User;

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
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        new SpringApplicationBuilder(Main.class)
                .properties("server.port = 3000")
                .run(args);
        try
        {
            Cat.setConnection(DriverManager.getConnection("jdbc:mysql://localhost:3340/employees?user=root&password=root"));
            var db = Cat.buildDataBase(AppDataBase.class, true);
            db.userDao().insertUser(User.builder().id(1).name("Usuario1").apellido("Apellido1").build());
            db.userDao().insertUser(User.builder().id(2).name("Usuario2").apellido("Apellido2").build());
            System.out.println(db.userDao().getAll());
            System.out.println(db.userDao().insertUser(User.builder().apellido("turco").name("tulio").build()));
            System.out.println(db.userDao().insertUser(User.builder().name("juan").build()));
            System.out.println(db.userDao().updateUser(User.builder().id(1).name("xddddd").build()));
            System.out.println(db.productDao().insert(Product.builder().id(5).name("chetos").price(45).build()));
            System.out.println(db.userDao().deleteUser(User.builder().id(1).build()));
            System.out.println(db.userDao().deleteUser(User.builder().id(2).build()));
            System.out.println(db.userDao().getAll());
            System.out.println(db.userDao().getById(4));
        }catch (SQLException  e)
        {
            e.printStackTrace();
        }

    }
}