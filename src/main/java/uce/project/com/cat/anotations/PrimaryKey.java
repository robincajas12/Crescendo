package uce.project.com.cat.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que marca un campo como la clave primaria de una entidad de base de datos.
 * Permite especificar si la clave primaria es autoincremental.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {
    /**
     * Indica si la clave primaria es autoincremental.
     * Por defecto es `false`.
     * @return `true` si la clave primaria se autoincrementa, `false` en caso contrario.
     */
    boolean autoIncrement() default false;
}
