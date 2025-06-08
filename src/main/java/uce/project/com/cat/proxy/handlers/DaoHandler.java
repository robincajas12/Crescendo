package uce.project.com.cat.proxy.handlers;

import uce.project.com.cat.Cat;
import uce.project.com.cat.anotations.*;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DaoHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.isAnnotationPresent(Query.class)) return getMany(proxy, method, args);
        else if(method.isAnnotationPresent(Insert.class) && args.length == 1) return insertOne(proxy,method, args);
        throw new RuntimeException(method.getName() +  " error make sure your dao implementation is right");
    }
    public Object insertOne(Object proxy, Method method, Object[] args) throws Throwable {
        if(args.length != 1) throw new RuntimeException(method.getName()+ " must receive just one parameter");
        Connection connection = Cat.getConnection();
        StringBuilder builder = new StringBuilder();
        StringBuilder paramsBuilder = new StringBuilder();
        var item = args[0];
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
        return st.execute();
    }

    public Object getMany(Object proxy, Method method, Object[] args) throws Throwable
    {
        String query = method.getAnnotation(Query.class).value();
        if (query == null) throw new Error(Query.class.getName() + " is not implemented in " + method.getName());
        Connection connection = Cat.getConnection();
        Type returnType = method.getGenericReturnType();
        if(returnType instanceof ParameterizedType)
        {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type actualType = parameterizedType.getActualTypeArguments()[0];

            if (!(actualType instanceof Class<?> entityClass)) {
                throw new IllegalArgumentException("Cannot determine the entity class from generic return type.");
            }
            var st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
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
