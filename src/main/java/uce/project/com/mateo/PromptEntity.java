package uce.project.com.mateo;

import lombok.*;
import uce.project.com.cat.anotations.ColumnInfo;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.PrimaryKey;

/**
 * Clase que representa una entidad de prompt en la base de datos.
 * Utiliza anotaciones de Lombok para la generación automática de getters, setters, builder, constructores sin y con argumentos.
 * La anotación `@Entity("prompts")` la marca como una entidad de base de datos con el nombre de tabla "prompts".
 */
@Getter()
@Setter()
@Builder()
@NoArgsConstructor()
@AllArgsConstructor()
@Entity("prompts")
public class PromptEntity {
  /**
   * Identificador único del prompt.
   * Anotado con `@ColumnInfo(name = "id")` para mapear a la columna 'id' en la base de datos.
   * Anotado con `@PrimaryKey(autoIncrement = true)` para indicar que es la clave primaria y se autoincrementa.
   */
  @ColumnInfo(name = "id")
  @PrimaryKey(autoIncrement = true)
  private Integer id;

  /**
   * El texto del prompt.
   * Anotado con `@ColumnInfo(name = "prompt", params = "255")` para mapear a la columna 'prompt' con una longitud máxima de 255.
   */
  @ColumnInfo(name = "prompt", params = "255")
  private String prompt;
}
