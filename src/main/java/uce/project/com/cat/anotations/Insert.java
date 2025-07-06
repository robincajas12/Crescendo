package uce.project.com.cat.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotación de marcador que indica que un método en una interfaz DAO
 * realiza una operación de inserción de una nueva entidad en la base de datos.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Insert {
}
