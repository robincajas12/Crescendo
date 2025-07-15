package uce.project.com.server.auth.shared.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO (Data Transfer Object) para la creación de un nuevo usuario.
 * Contiene la información necesaria para registrar un usuario en el sistema.
 * Utiliza Lombok para generar automáticamente los métodos toString, getter y setter.
 */
@ToString
@Getter
@Setter
public class CreateUserDto {
  /**
   * El nombre del usuario.
   */
  private String name;
  /**
   * La dirección de correo electrónico del usuario, que también sirve como identificador único.
   */
  private String email;
  /**
   * La contraseña del usuario.
   */
  private String password;
}
