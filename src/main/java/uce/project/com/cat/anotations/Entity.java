package uce.project.com.cat.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Para crear tablas usar esta anotación en la cabecera de la clase
 * la entidad tiene se tiene que ponser el nombre de la tabla
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    String value();
}
