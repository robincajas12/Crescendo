package uce.project.com.cat.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotación utilizada para mapear parámetros de métodos en interfaces DAO
 * a parámetros con nombre en consultas SQL.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface P {
    /**
     * El nombre del parámetro en la consulta SQL.
     * @return El nombre del parámetro.
     */
    String value();
}
