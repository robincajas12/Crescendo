package uce.project.com.cat.proxy.handlers;

import com.google.j2objc.annotations.OnDealloc;
import uce.project.com.cat.Cat;
import uce.project.com.cat.anotations.*;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manejador de invocación para interfaces DAO (Data Access Object).
 * Intercepta las llamadas a métodos anotados con {@link Query}, {@link Insert}, {@link Update} o {@link Delete}
 * y ejecuta las operaciones de base de datos correspondientes.
 */
public class DaoHandler implements InvocationHandler {
    /**
     * Procesa las invocaciones de métodos en las interfaces DAO.
     * Redirige la llamada al método apropiado según la anotación presente en el método invocado.
     * @param proxy La instancia de proxy en la que se invocó el método.
     * @param method El objeto Method correspondiente al método invocado.
     * @param args Un array de objetos que contiene los valores de los argumentos pasados al método invocado.
     * @return El resultado de la operación de base de datos.
     * @throws Throwable Si ocurre un error durante la invocación o si la implementación del DAO es incorrecta.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.isAnnotationPresent(Query.class)) return getMany(proxy, method, args);
        else if(method.isAnnotationPresent(Insert.class) && args.length == 1) return insertOne(proxy,method, args);
        else if(method.isAnnotationPresent(Update.class) && args.length == 1) return updateOne(proxy, method, args);
        else if(method.isAnnotationPresent(Delete.class) && args.length == 1) return deleteOne(proxy,method,args);
        throw new RuntimeException(method.getName() +  " error make sure your dao implementation is right");
    }

    /**
     * Realiza una operación de actualización en la base de datos.
     * Espera un único argumento que debe ser una entidad anotada con {@link Entity}.
     * Construye una sentencia SQL UPDATE basada en los campos de la entidad y su clave primaria.
     * @param proxy La instancia de proxy.
     * @param method El método invocado (anotado con {@link Update}).
     * @param args Los argumentos del método, esperando una única entidad.
     * @return `true` si la actualización fue exitosa, `false` en caso contrario.
     * @throws Exception Si el número de argumentos es incorrecto, el argumento es nulo, o la entidad no es válida.
     */
    private Object updateOne(Object proxy, Method method, Object[] args) throws Exception {
        if(args.length != 1) throw new RuntimeException(method.getName()+ " must receive just one parameter");
        var item = args[0];
        if(item == null) throw new RuntimeException("The parameter of " + method.getName() + "doesn't have to be null");
        if(!item.getClass().isAnnotationPresent(Entity.class)) throw new RuntimeException(" the parameter of " + method.getName() + " needs to be an entity");
        var tableName = item.getClass().getAnnotation(Entity.class).value();
        var declaredFields = item.getClass().getDeclaredFields();
        Connection connection = Cat.getConnection();
        StringBuilder builder = new StringBuilder();
        StringBuilder builderWhere = new StringBuilder();
        builder.append(String.format("UPDATE %s SET ", tableName));
        Optional<Object> primaryKey = Optional.empty();
        List<Object> params= new ArrayList<>();
        for(var declaredField : declaredFields)
        {
            declaredField.setAccessible(true);
            var value = declaredField.get(item);
            if(declaredField.isAnnotationPresent(PrimaryKey.class))
            {
                var pkAnotation = declaredField.getAnnotation(PrimaryKey.class);
                var columName = declaredField.getAnnotation(ColumnInfo.class).name();
                if (value == null) throw new RuntimeException("CatORM needs an ID to update field");
                builderWhere.append(String.format("WHERE %s = ?", columName));
                primaryKey = Optional.of(value);
                continue;
            }
            if(declaredField.isAnnotationPresent(ColumnInfo.class))
            {
                if(value == null) continue;
                var columName = declaredField.getAnnotation(ColumnInfo.class).name();
                builder.append(String.format("%s = ?,", columName));
                params.add(value);
            }
        }
        builder = new StringBuilder(builder.substring(0, builder.length() - 1));
        builder.append(" ").append(builderWhere).append(";");
        System.out.println(builder);
        System.out.println(primaryKey.get());
        String sql = builder.toString();
        var st = connection.prepareStatement(sql);
        int index = 0;
        while (index < params.size())
        {
            System.out.println(index+1 + " " + params.get(index));
            st.setObject(index+1, params.get(index));
            index++;
        }
        st.setObject(index+1, primaryKey.get());
        System.out.println(sql);
        return st.execute();
    }

    /**
     * Realiza una operación de eliminación en la base de datos.
     * Espera un único argumento que debe ser una entidad anotada con {@link Entity}.
     * Construye una sentencia SQL DELETE basada en la clave primaria de la entidad.
     * @param proxy La instancia de proxy.
     * @param method El método invocado (anotado con {@link Delete}).
     * @param args Los argumentos del método, esperando una única entidad.
     * @return `true` si la eliminación fue exitosa, `false` en caso contrario.
     * @throws IllegalAccessException Si no se puede acceder a un campo de la entidad.
     * @throws SQLException Si ocurre un error de SQL.
     * @throws RuntimeException Si el número de argumentos es incorrecto, el argumento es nulo, la entidad no es válida, o la clave primaria es nula.
     */
    private Object deleteOne(Object proxy, Method method, Object[] args) throws IllegalAccessException, SQLException {
        if(args.length != 1) throw new RuntimeException(method.getName()+ " must receive just one parameter");
        Connection connection = Cat.getConnection();
        StringBuilder builder = new StringBuilder();
        var item = args[0];
        if(item == null) throw new RuntimeException("The parameter of " + method.getName() + "doesn't have to be null");
        if(!item.getClass().isAnnotationPresent(Entity.class)) throw new RuntimeException(" the parameter of " + method.getName() + " needs to be an entity");
        var tableName = item.getClass().getAnnotation(Entity.class).value();
        var declaredFields = item.getClass().getDeclaredFields();
        Optional<Object> primaryKey = Optional.empty();
        builder.append(String.format("DELETE FROM %s WHERE", tableName));
        for(var declaredField : declaredFields)
        {
            declaredField.setAccessible(true);
            var value = declaredField.get(item);
            if(declaredField.isAnnotationPresent(PrimaryKey.class) && declaredField.isAnnotationPresent(ColumnInfo.class))
            {
                if(value != null) {
                    primaryKey = Optional.of(value);
                    builder.append(String.format(" %s = ?", declaredField.getAnnotation(ColumnInfo.class).name()));
                    break;
                }
            }
        }
        if(primaryKey.isEmpty()) throw new RuntimeException("primary key is null you can't update");
        String sql = builder.toString();
        System.out.println(sql);
        var st = connection.prepareStatement(sql);
        st.setObject(1,primaryKey.get());
        return st.execute();
    }

    /**
     * Realiza una operación de inserción en la base de datos.
     * Espera un único argumento que debe ser una entidad anotada con {@link Entity}.
     * Construye una sentencia SQL INSERT basada en los campos de la entidad.
     * @param proxy La instancia de proxy.
     * @param method El método invocado (anotado con {@link Insert}).
     * @param args Los argumentos del método, esperando una única entidad.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     * @throws Throwable Si ocurre un error durante la inserción (ej. número de argumentos incorrecto, argumento nulo, entidad no válida, o clave primaria nula y no autoincremental).
     */
    private Object insertOne(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if(args.length != 1) throw new RuntimeException(method.getName()+ " must receive just one parameter");
            Connection connection = Cat.getConnection();
            StringBuilder builder = new StringBuilder();
            StringBuilder paramsBuilder = new StringBuilder();
            var item = args[0];
            if(item == null) throw new RuntimeException("The parameter of " + method.getName() + "doesn't have to be null");
            if(!item.getClass().isAnnotationPresent(Entity.class)) throw new RuntimeException(" the parameter of " + method.getName() + " needs to be an entity");
            var tableName = item.getClass().getAnnotation(Entity.class).value();
            var declaredFields = item.getClass().getDeclaredFields();
            builder.append(String.format("INSERT INTO %s (", tableName));
            paramsBuilder.append("VALUES(");

            ArrayList<Object> values = new ArrayList<>();
            for(var declaredField : declaredFields)
            {
                declaredField.setAccessible(true);
                var value = declaredField.get(item);
                if(declaredField.isAnnotationPresent(PrimaryKey.class))
                {
                    var pkAnotation = declaredField.getAnnotation(PrimaryKey.class);
                    if(pkAnotation.autoIncrement()) continue;
                    else if(value == null) throw new Exception(declaredField.getName() + "doesn't have to be null because is a primary key");
                }
                if(declaredField.isAnnotationPresent(ColumnInfo.class))
                {
                    var columName = declaredField.getAnnotation(ColumnInfo.class).name();
                    builder.append(String.format("%s,", columName));
                    paramsBuilder.append("?,");
                    values.add(value);
                }
            }
            builder = new StringBuilder(builder.substring(0, builder.length() - 1));
            paramsBuilder = new StringBuilder(paramsBuilder.substring(0, paramsBuilder.length()-1));
            builder.append(") ");
            paramsBuilder.append(");");
            String sql = builder.append(paramsBuilder).toString();
            var st = connection.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++)
            {
                st.setObject(i+1, values.get(i));
            }
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Ejecuta una consulta SQL y mapea los resultados a una lista de objetos de entidad.
     * Espera que el método invocado esté anotado con {@link Query}.
     * @param proxy La instancia de proxy.
     * @param method El método invocado (anotado con {@link Query}).
     * @param args Los argumentos del método, que se utilizan para reemplazar los parámetros en la consulta SQL.
     * @return Una lista de objetos de entidad que representan los resultados de la consulta.
     * @throws Throwable Si ocurre un error durante la ejecución de la consulta o el mapeo de resultados.
     */
    private Object getMany(Object proxy, Method method, Object[] args) throws Throwable
    {
        String query = method.getAnnotation(Query.class).value();
        if (query == null) throw new Error(Query.class.getName() + " is not implemented in " + method.getName());
        StringBuilder queryBuilder = new StringBuilder(query);
        Connection connection = Cat.getConnection();
        Type returnType = method.getGenericReturnType();
        if(args != null)
        {
            for (var param : method.getParameters())
            {
                if (param.isAnnotationPresent(P.class))
                {
                    queryBuilder = new StringBuilder(queryBuilder.toString().replace(String.format(":%s", param.getAnnotation(P.class).value()), "?"));
                }

            }
            System.out.println(queryBuilder);
        }
        if(returnType instanceof ParameterizedType)
        {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type actualType = parameterizedType.getActualTypeArguments()[0];

            if (!(actualType instanceof Class<?> entityClass)) {
                throw new IllegalArgumentException("Cannot determine the entity class from generic return type.");
            }

            var st = connection.prepareStatement(queryBuilder.toString());
            if(args !=  null)
            {
                int i = 1;
                for(var arg : args)
                {
                    st.setObject(i,arg);
                    i++;
                }
            }
            ResultSet rs = st.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            var constructorEntity = entityClass.getDeclaredConstructor();

            List<Object> res = new ArrayList<>();
            while (rs.next()) {
                constructorEntity.setAccessible(true);
                var entityInstance = constructorEntity.newInstance();
                for (var field : ((Class<?>) actualType).getDeclaredFields()) {

                    String columnName = field.getAnnotation(ColumnInfo.class).name();
                    var value = rs.getObject(columnName);
                    field.setAccessible(true);
                    field.set(entityInstance,value);

                }
                res.add(entityInstance);
            }
            return res;
        }

        return null;
    }
}
