package uce.project.com.cat.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotación que marca una clase como una entidad de base de datos.
 * Se utiliza en la cabecera de una clase para indicar que representa una tabla en la base de datos.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    /**
     * El nombre de la tabla en la base de datos a la que se mapea esta entidad.
     * @return El nombre de la tabla.
     */
    String value();
}
