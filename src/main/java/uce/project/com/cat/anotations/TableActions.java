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

/**
 * Interfaz que define acciones estáticas relacionadas con la gestión de tablas de base de datos.
 * Proporciona métodos para generar sentencias SQL de creación de tablas, verificar la existencia de tablas y eliminarlas.
 */
public interface TableActions {
    /**
     * Genera una sentencia SQL `CREATE TABLE` a partir de una clase de entidad.
     * Analiza las anotaciones {@link Entity}, {@link ColumnInfo} y {@link PrimaryKey} de la clase
     * para construir la sentencia SQL adecuada.
     * @param entity La clase de entidad de la cual generar la sentencia SQL.
     * @return Una cadena que contiene la sentencia SQL `CREATE TABLE`.
     * @throws RuntimeException Si la clase de entidad no tiene la anotación {@link Entity} o si el nombre de la entidad está vacío.
     */
    static String getSql(Class<?> entity)
    {
        Map<String, SqlColumnInfo> map = new HashMap<>();
        Entity entityAnotation = entity.getAnnotation(Entity.class);

        // Verifica si la entidad tiene la anotación @Entity y si su valor no está vacío
        if(entityAnotation == null) throw new RuntimeException(entity.getName() + "Must have Entity anotation");
        if(entityAnotation.value().trim().isEmpty()) throw new RuntimeException(entity.getName()+ "Must not be empty");

        // Itera sobre los campos declarados de la entidad para extraer información de columna
        var fields = entity.getDeclaredFields();
        Arrays.stream(fields).forEach(field ->{
            AtomicReference<String> key = new AtomicReference<>();
            AtomicReference<SqlColumnInfo.Builder> builder = new AtomicReference<>(SqlColumnInfo.builder());
            Class<?> fieldType = field.getType();
            IDataType dataType = SqlTypes.get(fieldType);

            builder.set(builder.get().type(dataType));

            // Procesa las anotaciones @ColumnInfo y @PrimaryKey de cada campo
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

        // Construye la sentencia SQL CREATE TABLE
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
        // Elimina la última coma y cierra el paréntesis
        sqlCreate = new StringBuilder(sqlCreate.substring(0, sqlCreate.length() - 1));
        sqlCreate.append("\n");
        sqlCreate.append(");");
        System.out.println(sqlCreate);
        return sqlCreate.toString();
    }

    /**
     * Verifica si una tabla existe en la base de datos.
     * @param connection La conexión a la base de datos.
     * @param tableName El nombre de la tabla a verificar.
     * @return `true` si la tabla existe, `false` en caso contrario.
     * @throws SQLException Si ocurre un error de acceso a la base de datos.
     */
    public static boolean doesTableExist(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData dbMeta = connection.getMetaData();
        try (ResultSet rs = dbMeta.getTables(null, null, tableName, new String[] {"TABLE"})) {
            return rs.next();
        }
    }

    /**
     * Elimina una tabla de la base de datos si existe.
     * @param connection La conexión a la base de datos.
     * @param tableName El nombre de la tabla a eliminar.
     * @return `true` si la tabla fue eliminada exitosamente o no existía, `false` si ocurrió un error.
     */
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
