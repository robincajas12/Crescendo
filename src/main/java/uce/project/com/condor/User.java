package uce.project.com.condor;

import lombok.*;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.PrimaryKey;
import uce.project.com.cat.anotations.ColumnInfo;

/**
 * Clase que representa un usuario en el sistema.
 * Utiliza anotaciones de Lombok para la generación automática de getters, toString, builder, constructores sin y con argumentos.
 * La anotación `@Entity("users")` la marca como una entidad de base de datos con el nombre de tabla "users".
 */
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity("users") //nombre de la tabla en la base de datos
public class User {

    /**
     * Identificador único del usuario.
     * Anotado con `@ColumnInfo(name = "id")` para mapear a la columna 'id' en la base de datos.
     * Anotado con `@PrimaryKey(autoIncrement = true)` para indicar que es la clave primaria y se autoincrementa.
     */
    @ColumnInfo(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Integer id;

    /**
     * Nombre del usuario.
     * Anotado con `@ColumnInfo(name = "name", params = "255")` para mapear a la columna 'name' con una longitud máxima de 255.
     */
    @ColumnInfo(name = "name", params = "255")
    private String name;

    /**
     * Correo electrónico del usuario.
     * Anotado con `@ColumnInfo(name = "email", params = "255")` para mapear a la columna 'email' con una longitud máxima de 255.
     */
    @ColumnInfo(name = "email", params = "255")
    private String email;

    /**
     * Contraseña del usuario.
     * Anotado con `@ColumnInfo(name = "password", params = "255")` para mapear a la columna 'password' con una longitud máxima de 255.
     */
    @ColumnInfo(name = "password", params = "255")
    private String password;
}