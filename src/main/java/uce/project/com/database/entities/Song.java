package uce.project.com.database.entities;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.PrimaryKey;
import uce.project.com.cat.anotations.ColumnInfo;
import lombok.*;

/**
 * Clase que representa una canción en el sistema.
 * Utiliza anotaciones de Lombok para la generación automática de getters, toString, builder, constructores sin y con argumentos.
 * La anotación `@Entity("Song")` la marca como una entidad de base de datos con el nombre de tabla "Song".
 */
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity("Song")
public class Song {
    /**
     * Identificador único de la canción.
     * Anotado con `@ColumnInfo(name = "id")` para mapear a la columna 'id' en la base de datos.
     * Anotado con `@PrimaryKey(autoIncrement = true)` para indicar que es la clave primaria y se autoincrementa.
     */
    @ColumnInfo(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Integer id;

    /**
     * Nombre de la canción.
     * Anotado con `@ColumnInfo(name = "nombre", params = "255")` para mapear a la columna 'nombre' con una longitud máxima de 255.
     */
    @ColumnInfo(name = "nombre", params = "255")
    private String nombre;

    /**
     * ID del usuario al que pertenece la canción.
     * Anotado con `@ColumnInfo(name = "userId")` para mapear a la columna 'userId'.
     */
    @ColumnInfo(name = "userId")
    private Integer userId;

    /**
     * Género musical de la canción.
     * Anotado con `@ColumnInfo(name = "genero", params = "100")` para mapear a la columna 'genero' con una longitud máxima de 100.
     */
    @ColumnInfo(name = "genero", params = "100")
    private String genero;

    /**
     * Notas musicales o partitura de la canción.
     * Anotado con `@ColumnInfo(name = "notasMusicales", params = "5000")` para mapear a la columna 'notasMusicales' con una longitud máxima de 5000.
     */
    @ColumnInfo(name = "notasMusicales", params = "5000")
    private String notasMusicales;
}