package uce.project.com.server.auth.shared.dto;

import lombok.Getter;

/**
 * DTO (Data Transfer Object) para la solicitud de inicio de sesión.
 * Contiene las credenciales (correo electrónico y contraseña) para autenticar a un usuario.
 * Utiliza Lombok para generar automáticamente el método getter.
 */
@Getter
public class SigninRequestDto {
  /**
   * La dirección de correo electrónico del usuario que intenta iniciar sesión.
   */
  private String email;
  /**
   * La contraseña del usuario que intenta iniciar sesión.
   */
  private String password;
}
