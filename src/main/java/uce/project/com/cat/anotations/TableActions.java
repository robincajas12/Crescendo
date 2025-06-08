package uce.project.com.cat.anotations;

import uce.project.com.cat.SqlColumnInfo;
import uce.project.com.cat.types.SqlTypes;
import uce.project.com.cat.types.inter.IDataType;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public interface TableActions {
    static String getSql(Class<?> entity)
    {
        Map<String, SqlColumnInfo> map = new HashMap<>();
        Entity entityAnotation = entity.getAnnotation(Entity.class);

        if(entityAnotation == null) throw new RuntimeException(entity.getName() + "Must have Entity anotation");
        if(entityAnotation.value().trim().isEmpty()) throw new RuntimeException(entity.getName()+ "Must not be empty");
        var fields = entity.getDeclaredFields();
        Arrays.stream(fields).forEach(field ->{
            AtomicReference<String> key = new AtomicReference<>();
            AtomicReference<SqlColumnInfo.Builder> builder = new AtomicReference<>(SqlColumnInfo.builder());
            Class<?> fieldType = field.getType();
            IDataType dataType = SqlTypes.get(fieldType);

            builder.set(builder.get().type(dataType));

            Arrays.stream(field.getAnnotations()).forEach(annotation -> {
                if(annotation.annotationType().equals(ColumnInfo.class))
                {
                    ColumnInfo columnInfo = (ColumnInfo) annotation;
                    builder.set(builder.get().columnName(columnInfo.name()).params(columnInfo.params()));
                    key.set(columnInfo.name());
                    return;
                }
                if(annotation.annotationType().equals(PrimaryKey.class))
                {
                    PrimaryKey pk = (PrimaryKey) annotation;
                    builder.set(builder.get().isPrimaryKey(true).autoIncrement(pk.autoIncrement()));
                }
            });
            map.put(key.get(), builder.get().build());
        });
        StringBuilder sqlCreate = new StringBuilder();

        sqlCreate.append(String.format("CREATE TABLE %s(", entityAnotation.value()));
        var keys = map.keySet();
        var keyList = new java.util.ArrayList<>(keys.stream().toList());
        Collections.reverse(keyList);
        for(var key : keyList)
        {
            sqlCreate.append("\n");
            SqlColumnInfo cInfo = map.get(key);
            sqlCreate.append(String.format(cInfo.columnName()));
            sqlCreate.append(" ");
            sqlCreate.append(String.format(cInfo.type().sqlType(), cInfo.params()));
            sqlCreate.append(cInfo.isPrimaryKey()? " PRIMARY KEY" : "");
            sqlCreate.append(cInfo.autoIncrement()? " AUTO_INCREMENT":"");
            sqlCreate.append(",");
        }
        sqlCreate = new StringBuilder(sqlCreate.substring(0, sqlCreate.length() - 1));
        sqlCreate.append("\n");
        sqlCreate.append(");");
        System.out.println(sqlCreate);
        return sqlCreate.toString();
    }
    public static boolean doesTableExist(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData dbMeta = connection.getMetaData();
        try (ResultSet rs = dbMeta.getTables(null, null, tableName, new String[] {"TABLE"})) {
            return rs.next();
        }
    }
    static boolean dropTable(Connection connection, String tableName)
    {
        System.out.println(tableName);
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
