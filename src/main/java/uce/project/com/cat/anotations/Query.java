package uce.project.com.cat.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que especifica una consulta SQL para un método en una interfaz DAO.
 * El valor de la anotación es la cadena de la consulta SQL a ejecutar.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Query {
    /**
     * La cadena de la consulta SQL a ejecutar.
     * @return La consulta SQL.
     */
    String value();
}
