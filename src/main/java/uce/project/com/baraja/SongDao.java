package uce.project.com.baraja;
import uce.project.com.cat.anotations.*;
import java.util.List;

/**
 * Interfaz DAO (Data Access Object) para la entidad {@link Song}.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas sobre la tabla de canciones.
 * La anotación `@Dao` marca esta interfaz como un DAO que será implementado automáticamente por el framework.
 */
@Dao
public interface SongDao {
    /**
     * Recupera todas las canciones de la base de datos.
     * La anotación `@Query` especifica la consulta SQL a ejecutar.
     * @return Una lista de todas las canciones.
     */
    @Query("SELECT * FROM Song")
    List<Song> getAll();

    /**
     * Recupera una canción por su ID.
     * La anotación `@P("id")` mapea el parámetro `id` del método al parámetro `:id` en la consulta SQL.
     * @param id El ID de la canción a buscar.
     * @return Una lista de canciones que coinciden con el ID (normalmente una o ninguna).
     */
    @Query("SELECT * FROM Song WHERE id = :id")
    List<Song> getSongByID(@P("id") Integer id);

    /**
     * Recupera canciones por su género.
     * @param genero El género de las canciones a buscar.
     * @return Una lista de canciones que pertenecen al género especificado.
     */
    @Query("SELECT * FROM Song WHERE genero = :genero")
    List<Song> getSongsByGender(@P("genero") String genero);

    /**
     * Filtra canciones por su nombre, utilizando un patrón de búsqueda.
     * @param nombre El patrón de nombre a buscar (ej. "%rock%").
     * @return Una lista de canciones cuyos nombres coinciden con el patrón.
     */
    @Query("SELECT * FROM Song WHERE nombre LIKE :nombre")
    List<Song> filterByName(@P("nombre") String nombre);

    /**
     * Inserta una nueva canción en la base de datos.
     * La anotación `@Insert` indica que este método realiza una operación de inserción.
     * @param song El objeto Song a insertar.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     */
    @Insert
    boolean insertSong(Song song);

    /**
     * Actualiza una canción existente en la base de datos.
     * La anotación `@Update` indica que este método realiza una operación de actualización.
     * @param song El objeto Song a actualizar.
     * @return `true` si la actualización fue exitosa, `false` en caso contrario.
     */
    @Update
    boolean updateSong(Song song);

    /**
     * Elimina una canción de la base de datos.
     * La anotación `@Delete` indica que este método realiza una operación de eliminación.
     * @param song El objeto Song a eliminar.
     * @return `true` si la eliminación fue exitosa, `false` en caso contrario.
     */
    @Delete
    boolean deleteSong(Song song);
}