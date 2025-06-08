package uce.project.com.cat.proxy.handlers;

import uce.project.com.cat.Cat;
import uce.project.com.cat.anotations.ColumnInfo;
import uce.project.com.cat.anotations.Query;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DaoHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
