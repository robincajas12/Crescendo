package uce.project.com.cat.types.inter;

public interface IDataType {
    boolean match(Class<?> clazz);
    String sqlType();
    String stringifyType();
}
