package uce.project.com.cat;

import uce.project.com.cat.anotations.Database;
import uce.project.com.cat.anotations.TableActions;
import uce.project.com.cat.proxy.handlers.AppDatabaseHandler;
import uce.project.com.main.AppDataBase;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cat {
    public static Connection connection = null;

    public static void setConnection(Connection connection) {
        Cat.connection = connection;
    }

    public static Connection getConnection() {
        if(connection == null) throw new RuntimeException("please set the connection to the database");
        return connection;
    }

    public static <T extends AppDataBase> T buildDataBase(Class<T> appDatabase)
    {
        var anotation = appDatabase.getAnnotation(Database.class);
        Class<?>[] entities = anotation.entities();
        for (var entity: entities)
        {
            try
            {
                var st = getConnection().createStatement();
                st.executeUpdate(TableActions.getSql(entity));
                new CreateTable().createTable(entity);
                insertFiveUsers(getConnection());
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return (T) Proxy.newProxyInstance(appDatabase.getClassLoader(), new Class[]{appDatabase}, new AppDatabaseHandler());


    }
    public static void insertFiveUsers(Connection connection) throws SQLException {
        String sql = "INSERT INTO User (id, name, apellido, gender) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // User 1
            ps.setInt(1, 1);
            ps.setString(2, "John");
            ps.setString(3, "Doe");
            ps.setBoolean(4, true);
            ps.executeUpdate();

            // User 2
            ps.setInt(1, 2);
            ps.setString(2, "Jane");
            ps.setString(3, "Smith");
            ps.setBoolean(4, false);
            ps.executeUpdate();

            // User 3
            ps.setInt(1, 3);
            ps.setString(2, "Alice");
            ps.setString(3, "Johnson");
            ps.setBoolean(4, false);
            ps.executeUpdate();

            // User 4
            ps.setInt(1, 4);
            ps.setString(2, "Bob");
            ps.setString(3, "Williams");
            ps.setBoolean(4, true);
            ps.executeUpdate();

            // User 5
            ps.setInt(1, 5);
            ps.setString(2, "Eve");
            ps.setString(3, "Brown");
            ps.setBoolean(4, false);
            ps.executeUpdate();
        }
    }


}
