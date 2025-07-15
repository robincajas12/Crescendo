package uce.project.com.database.daos;

import uce.project.com.cat.anotations.*;
import uce.project.com.database.entities.PromptEntity;

import java.util.List;

/**
 * Interfaz DAO (Data Access Object) para la entidad {@link PromptEntity}.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas sobre la tabla de prompts.
 * La anotación `@Dao` (implícita por el uso de otras anotaciones de CatORM) marca esta interfaz como un DAO.
 */
public interface PromptDao {

  /**
   * Recupera un prompt por su ID.
   * La anotación `@Query` especifica la consulta SQL a ejecutar.
   * La anotación `@P("id")` mapea el parámetro `id` del método al parámetro `:id` en la consulta SQL.
   * @param id El ID del prompt a buscar.
   * @return El objeto PromptEntity que coincide con el ID, o `null` si no se encuentra.
   */
  @Query("SELECT * FROM promt WHERE id = :id")
  PromptEntity getPromtById(@P("id") Integer id);

  /**
   * Filtra prompts por una palabra clave contenida en el texto del prompt.
   * @param word La palabra clave a buscar.
   * @return El objeto PromptEntity que contiene la palabra clave, o `null` si no se encuentra.
   */
  @Query("SELECT * FROM promt WHERE prompt LIKE '%' || :word || '%'")
  PromptEntity filterByWord(@P("word") String word);

  /**
   * Recupera todos los prompts de la base de datos.
   * @return Una lista de todos los objetos PromptEntity.
   */
  @Query("SELECT * FROM promt")
  List<PromptEntity> getAll();

  /**
   * Inserta un nuevo prompt en la base de datos.
   * La anotación `@Insert` indica que este método realiza una operación de inserción.
   * @param promtEntity El objeto PromptEntity a insertar.
   * @return `true` si la inserción fue exitosa, `false` en caso contrario.
   */
  @Insert()
  boolean insertPromt(PromptEntity promtEntity);

  /**
   * Actualiza un prompt existente en la base de datos.
   * La anotación `@Update` indica que este método realiza una operación de actualización.
   * @param promtEntity El objeto PromptEntity a actualizar.
   * @return `true` si la actualización fue exitosa, `false` en caso contrario.
   */
  @Update()
  boolean updatePromt(PromptEntity promtEntity);

  /**
   * Elimina un prompt de la base de datos.
   * La anotación `@Delete` indica que este método realiza una operación de eliminación.
   * @param promtEntity El objeto PromptEntity a eliminar.
   * @return `true` si la eliminación fue exitosa, `false` en caso contrario.
   */
  @Delete()
  boolean deletePromt(PromptEntity promtEntity);
}
