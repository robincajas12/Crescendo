package uce.project.com.cat;

import uce.project.com.cat.types.inter.IDataType;

/**
 * Record que representa la información de una columna SQL, incluyendo su tipo, nombre, si es clave primaria,
 * si es autoincremental y parámetros adicionales.
 * @param type El tipo de dato SQL de la columna.
 * @param columnName El nombre de la columna en la base de datos.
 * @param isPrimaryKey Indica si la columna es parte de la clave primaria.
 * @param autoIncrement Indica si la columna es autoincremental.
 * @param params Parámetros adicionales para la columna (ej. longitud para VARCHAR).
 */
public record SqlColumnInfo(
        IDataType type,
        String columnName,
        boolean isPrimaryKey,
        boolean autoIncrement,
        String params
){
    /**
     * Constructor privado utilizado por el Builder para crear una instancia de SqlColumnInfo.
     * @param builder El objeto Builder que contiene la información de la columna.
     */
    private SqlColumnInfo(Builder builder)
    {
        this(builder.type, builder.columnName, builder.isPrimaryKey, builder.autoIncrement, builder.params);
    }

    /**
     * Crea y devuelve una nueva instancia del Builder para construir un objeto SqlColumnInfo.
     * @return Una nueva instancia de Builder.
     */
    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * Clase interna estática para construir objetos {@link SqlColumnInfo} de manera fluida.
     */
    public static class Builder
    {
        IDataType type;
        String columnName;
        boolean isPrimaryKey;
        boolean autoIncrement;
        String params;

        /**
         * Establece el tipo de dato SQL para la columna.
         * @param type El tipo de dato SQL.
         * @return El Builder actual para encadenamiento.
         */
        public Builder type(IDataType type)
        {
            this.type = type;
            return this;
        }

        /**
         * Establece los parámetros adicionales para la columna.
         * @param params Los parámetros de la columna.
         * @return El Builder actual para encadenamiento.
         */
        public Builder params(String params)
        {
            this.params = params;
            return this;
        }

        /**
         * Establece el nombre de la columna.
         * @param columnName El nombre de la columna.
         * @return El Builder actual para encadenamiento.
         */
        public Builder columnName(String columnName)
        {
            this.columnName = columnName;
            return this;
        }

        /**
         * Establece si la columna es una clave primaria.
         * @param isPrimaryKey `true` si es clave primaria, `false` en caso contrario.
         * @return El Builder actual para encadenamiento.
         */
        public Builder isPrimaryKey(boolean isPrimaryKey)
        {
            this.isPrimaryKey = isPrimaryKey;
            return this;
        }

        /**
         * Construye y devuelve una nueva instancia de {@link SqlColumnInfo} con los valores configurados.
         * @return Una nueva instancia de SqlColumnInfo.
         * @throws IllegalStateException Si el tipo o el nombre de la columna son nulos o vacíos.
         */
        public SqlColumnInfo build()
        {
            if (type == null || columnName == null || columnName.isBlank()) {
                throw new IllegalStateException("type y columnName no pueden ser nulos o vacíos");
            }
            return new SqlColumnInfo(this);
        }

        /**
         * Establece si la columna es autoincremental.
         * @param autoIncrement `true` si es autoincremental, `false` en caso contrario.
         * @return El Builder actual para encadenamiento.
         */
        public Builder autoIncrement(boolean autoIncrement)
        {
            this.autoIncrement = autoIncrement;
            return this;
        }
    }
}