package uce.project.com.cat;

import uce.project.com.cat.anotations.Database;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.TableActions;
import uce.project.com.cat.proxy.handlers.AppDatabaseHandler;
import uce.project.com.database.AppDataBase;

import java.lang.reflect.Proxy;
import java.sql.*;

/**
 * Clase principal para la interacción con la base de datos, actuando como un ORM (Object-Relational Mapping) ligero.
 * Proporciona métodos para establecer la conexión, obtenerla y construir la base de datos a partir de entidades anotadas.
 */
public class Cat {
    /**
     * Conexión estática a la base de datos.
     */
    public static Connection connection = null;

    /**
     * Establece la conexión a la base de datos.
     * @param connection Objeto Connection que representa la conexión a la base de datos.
     */
    static void setConnection(Connection connection) {
        Cat.connection = connection;
    }

    /**
     * Obtiene la conexión actual a la base de datos.
     * @return La conexión a la base de datos.
     * @throws RuntimeException Si la conexión no ha sido establecida previamente.
     */
    public static Connection getConnection() {
        if(connection == null) throw new RuntimeException("please set the connection to the database");
        return connection;
    }

    /**
     * Construye la base de datos a partir de una clase que extiende {@link AppDataBase}.
     * Crea las tablas si no existen, basándose en las entidades anotadas en la clase {@link Database}.
     * @param appDatabase La clase que extiende {@link AppDataBase} y está anotada con {@link Database}.
     * @param connection La conexión a la base de datos.
     * @param dropPrevTables Si es `true`, elimina las tablas existentes antes de crearlas.
     * @param <T> El tipo de la clase AppDataBase.
     * @return Una instancia de la base de datos construida, que es un proxy para la interfaz AppDataBase.
     * @throws RuntimeException Si una entidad no tiene la anotación {@link Entity}.
     */
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
