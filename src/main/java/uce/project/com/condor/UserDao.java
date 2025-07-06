package uce.project.com.condor;

import uce.project.com.cat.anotations.Dao;
import uce.project.com.cat.anotations.Query;
import uce.project.com.cat.anotations.Insert;
import uce.project.com.cat.anotations.Update;
import uce.project.com.cat.anotations.Delete;
import uce.project.com.cat.anotations.P;
import java.util.List;

/**
 * Interfaz DAO (Data Access Object) para la entidad {@link User}.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas sobre la tabla de usuarios.
 * La anotación `@Dao` marca esta interfaz como un DAO que será implementado automáticamente por el framework.
 */
@Dao
public interface UserDao {

    /**
     * Recupera todos los usuarios de la base de datos.
     * La anotación `@Query` especifica la consulta SQL a ejecutar.
     * @return Una lista de todos los usuarios.
     */
    @Query("SELECT * FROM users")
    List<User> getAll();

    /**
     * Recupera un usuario por su ID.
     * La anotación `@P("id")` mapea el parámetro `id` del método al parámetro `:id` en la consulta SQL.
     * @param id El ID del usuario a buscar.
     * @return Una lista de usuarios que coinciden con el ID (normalmente uno o ninguno).
     */
    @Query("SELECT * FROM users WHERE id = :id")
    List<User> getById(@P("id") Integer id);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return Una lista de usuarios que coinciden con el correo electrónico (normalmente uno o ninguno).
     */
    @Query("SELECT * FROM users WHERE email = :email")
    List<User> findOneByEmail(@P("email") String email);

    /**
     * Busca usuarios por un patrón en su nombre de usuario.
     * @param username El patrón de nombre de usuario a buscar (ej. "%john%").
     * @return Una lista de usuarios cuyos nombres de usuario coinciden con el patrón.
     */
    @Query("SELECT * FROM users WHERE username LIKE :username")
    List<User> searchByUsername(@P("username") String username);

    /**
     * Inserta un nuevo usuario en la base de datos.
     * La anotación `@Insert` indica que este método realiza una operación de inserción.
     * @param user El objeto User a insertar.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     */
    @Insert
    boolean insertUser(User user);

    /**
     * Actualiza un usuario existente en la base de datos.
     * La anotación `@Update` indica que este método realiza una operación de actualización.
     * @param user El objeto User a actualizar.
     * @return `true` si la actualización fue exitosa, `false` en caso contrario.
     */
    @Update
    boolean updateUser(User user);

    /**
     * Elimina un usuario de la base de datos.
     * La anotación `@Delete` indica que este método realiza una operación de eliminación.
     * @param user El objeto User a eliminar.
     * @return `true` si la eliminación fue exitosa, `false` en caso contrario.
     */
    @Delete
    boolean deleteUser(User user);
}