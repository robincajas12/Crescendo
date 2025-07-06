package uce.project.com.cat.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotación que marca una clase como una base de datos.
 * Se utiliza para especificar las entidades (tablas) que forman parte de esta base de datos.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Database {
    /**
     * Un array de clases de entidad que pertenecen a esta base de datos.
     * Cada clase de entidad debe estar anotada con `@Entity`.
     * @return Las clases de entidad asociadas a esta base de datos.
     */
    Class<?>[] entities();
}
