package uce.project.com.cat;

import uce.project.com.cat.types.inter.IDataType;

public record SqlColumnInfo(
        IDataType type,
        String columnName,
        boolean isPrimaryKey,
        boolean autoIncrement,
        String params
){
    private SqlColumnInfo(Builder builder)
    {
        this(builder.type, builder.columnName, builder.isPrimaryKey, builder.autoIncrement, builder.params);
    }
    public static Builder builder()
    {
        return new Builder();
    }
    public static class Builder
    {
        IDataType type;
        String columnName;
        boolean isPrimaryKey;
        boolean autoIncrement;
        String params;
        public Builder type(IDataType type)
        {
            this.type = type;
            return this;
        }
        public Builder params(String params)
        {
            this.params = params;
            return this;
        }
        public Builder columnName(String columnName)
        {
            this.columnName = columnName;
            return this;
        }
        public Builder isPrimaryKey(boolean isPrimaryKey)
        {
            this.isPrimaryKey = isPrimaryKey;
            return this;
        }
        public SqlColumnInfo build()
        {
            if (type == null || columnName == null || columnName.isBlank()) {
                throw new IllegalStateException("type y columnName no pueden ser nulos o vacíos");
            }
            return new SqlColumnInfo(this);
        }
        public Builder autoIncrement(boolean autoIncrement)
        {
            this.autoIncrement = autoIncrement;
            return this;
        }
    }
}