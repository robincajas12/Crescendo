package uce.project.com.server.auth.shared.dto;

import lombok.Getter;

/**
 * DTO (Data Transfer Object) para la solicitud de inicio de sesión social.
 * Contiene la información básica (correo electrónico y nombre) para autenticar a un usuario a través de un proveedor social.
 * Utiliza Lombok para generar automáticamente el método getter.
 */
@Getter
public class SocialSigninRequestDto {
  /**
   * La dirección de correo electrónico del usuario proporcionada por el proveedor social.
   */
  private String email;
  /**
   * El nombre del usuario proporcionado por el proveedor social.
   */
  private String name;
}
