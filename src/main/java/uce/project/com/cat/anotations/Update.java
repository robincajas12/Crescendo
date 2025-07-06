package uce.project.com.cat.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotación de marcador que indica que un método en una interfaz DAO
 * realiza una operación de actualización de una entidad existente en la base de datos.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Update {
}
