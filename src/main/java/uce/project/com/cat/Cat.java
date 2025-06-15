package uce.project.com.cat;

import uce.project.com.cat.anotations.Database;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.TableActions;
import uce.project.com.cat.proxy.handlers.AppDatabaseHandler;
import uce.project.com.database.AppDataBase;

import java.lang.reflect.Proxy;
import java.sql.*;

public class Cat {
    public static Connection connection = null;

    static void setConnection(Connection connection) {
        Cat.connection = connection;
    }

    public static Connection getConnection() {
        if(connection == null) throw new RuntimeException("please set the connection to the database");
        return connection;
    }

    public static <T extends AppDataBase> T buildDataBase(Class<T> appDatabase, Connection connection,boolean dropPrevTables)
    {
        setConnection(connection);
        var anotation = appDatabase.getAnnotation(Database.class);
        Class<?>[] entities = anotation.entities();
        for (var entity: entities)
        {
            if(!entity.isAnnotationPresent(Entity.class)) throw new RuntimeException(entity.getName()+ " must have a Entity annotation");
            try
            {
                var st = getConnection().createStatement();
                if(dropPrevTables) TableActions.dropTable(connection, entity.getAnnotation(Entity.class).value());
                if(!TableActions.doesTableExist(connection,entity.getAnnotation(Entity.class).value())) st.executeUpdate(TableActions.getSql(entity));


            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return (T) Proxy.newProxyInstance(appDatabase.getClassLoader(), new Class[]{appDatabase}, new AppDatabaseHandler());


    }

}
