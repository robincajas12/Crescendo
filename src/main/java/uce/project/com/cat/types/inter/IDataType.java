package uce.project.com.cat.types.inter;

/**
 * Interfaz que define el comportamiento para los tipos de datos SQL.
 * Permite mapear tipos de datos Java a sus equivalentes SQL y formatear valores para sentencias SQL.
 */
public interface IDataType {
    /**
     * Verifica si el tipo de clase Java dado coincide con el tipo de dato SQL que representa esta interfaz.
     * @param clazz La clase Java a comparar.
     * @return `true` si la clase coincide, `false` en caso contrario.
     */
    boolean match(Class<?> clazz);

    /**
     * Devuelve la representación en cadena del tipo de dato SQL.
     * Por ejemplo, "INT", "VARCHAR(%s)", "BOOL".
     * @return La cadena del tipo de dato SQL.
     */
    String sqlType();

    /**
     * Devuelve una cadena de formato para representar un valor de este tipo de dato en una sentencia SQL.
     * Por ejemplo, para un String podría ser "\"%s\"" para incluir comillas.
     * @return La cadena de formato para el valor.
     */
    String stringifyType();
}
