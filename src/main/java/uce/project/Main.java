package uce.project;

import uce.project.com.main.AppDataBase;
import uce.project.com.cat.Cat;
import uce.project.com.cat.CreateTable;
import uce.project.com.main.User;

import java.sql.DriverManager;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/**
 * Breve descripción de la clase.
 * Más detalles opcionales.
 */
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        CreateTable c = new CreateTable();
       // c.createTable(User.class);

        try
        {
            Cat.setConnection(DriverManager.getConnection("jdbc:mysql://localhost:3340/employees?user=root&password=root"));
            var db = Cat.buildDataBase(AppDataBase.class);
            System.out.println(db.userDao().getAll());
            System.out.println(db.userDao().getGenderTrue());
        }catch (SQLException  e)
        {
            e.printStackTrace();
        }

    }
}