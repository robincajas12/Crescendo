package uce.project.com.cat.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación personalizada para proporcionar información sobre una columna de base de datos.
 * Se utiliza en campos de clases de entidad para mapearlos a columnas de tabla.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnInfo {
    /**
     * El nombre de la columna en la base de datos.
     * @return El nombre de la columna.
     */
    String name();

    /**
     * Parámetros adicionales para la columna, como la longitud para VARCHAR.
     * Por defecto es una cadena vacía.
     * @return Los parámetros de la columna.
     */
    String params() default "";
}
