package uce.project.com.server.auth.shared.dto;

import lombok.Builder;
import lombok.ToString;

/**
 * DTO (Data Transfer Object) para la respuesta de información de usuario.
 * Contiene los datos básicos de un usuario que se devuelven después de operaciones como registro o inicio de sesión.
 * Utiliza Lombok para generar automáticamente el constructor builder y el método toString.
 */
@Builder
@ToString
public class UserResponseDto {
  /**
   * El identificador único del usuario.
   */
  public Integer id;
  /**
   * La dirección de correo electrónico del usuario.
   */
  public String email;
  /**
   * El nombre del usuario.
   */
  public String name;
}
