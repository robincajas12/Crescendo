package uce.project.com.server.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uce.project.com.Main;
import uce.project.com.database.entities.User;
import uce.project.com.server.auth.shared.dto.SocialSigninRequestDto;
import uce.project.com.server.auth.shared.dto.UserResponseDto;
import uce.project.com.server.auth.shared.services.IHandler;

/**
 * Manejador para el registro de nuevos usuarios a través de Google.
 * Implementa la interfaz {@link IHandler} para procesar solicitudes de registro social.
 * Crea un nuevo usuario en la base de datos si no existe.
 */
@Service
public class GoogleSignupHandler implements IHandler<SocialSigninRequestDto> {
  /**
   * El siguiente manejador en la cadena de responsabilidad. En este caso, siempre es nulo ya que es el final de la cadena.
   */
  private IHandler<SocialSigninRequestDto> nextHandler;

  /**
   * Procesa la solicitud de registro social.
   * Crea un nuevo usuario con la información proporcionada y lo inserta en la base de datos.
   * @param request La solicitud de registro social que contiene la información del nuevo usuario.
   * @return Un {@link UserResponseDto} con la información del usuario recién creado.
   * @throws ResponseStatusException Si falla la creación del usuario en la base de datos.
   */
  @Override
  public UserResponseDto handleRequest(SocialSigninRequestDto request) {
    User user = User.builder()
            .email(request.getEmail())
            .password(null)
            .name(request.getName())
            .build();

    boolean wasSuccessful = Main.db.userDao().insertUser(user);

    if(!wasSuccessful) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user");
    }

    var userCreated = Main.db.userDao().findOneByEmail(request.getEmail()).get(0);

    return UserResponseDto.builder().id(userCreated.getId()).email(userCreated.getEmail()).name(userCreated.getName()).build();
  }

  /**
   * Establece el siguiente manejador en la cadena de responsabilidad.
   * Para este manejador, el siguiente siempre se establece en `null` ya que es el punto final de la creación de usuario.
   * @param nextHandler El siguiente manejador a establecer (será ignorado y establecido en null).
   */
  @Override
  public void setNextHandler(IHandler<SocialSigninRequestDto> nextHandler) {
    this.nextHandler = null;
  }
}
