package uce.project.com.mateo.service;

import org.springframework.stereotype.Service;
import uce.project.com.Main;
import uce.project.com.condor.User;
import uce.project.com.mateo.shared.dto.SocialSigninRequestDto;
import uce.project.com.mateo.shared.dto.UserResponseDto;
import uce.project.com.mateo.shared.services.IHandler;

import java.util.List;

/**
 * Manejador para el inicio de sesión de usuarios a través de Google.
 * Implementa la interfaz {@link IHandler} para procesar solicitudes de inicio de sesión social.
 * Si el usuario ya existe, devuelve sus datos; de lo contrario, pasa la solicitud al siguiente manejador en la cadena.
 */
@Service
public class GoogleSigninHandler implements IHandler<SocialSigninRequestDto> {
  /**
   * El siguiente manejador en la cadena de responsabilidad.
   */
  private IHandler<SocialSigninRequestDto> nextHandler;

  /**
   * Procesa la solicitud de inicio de sesión social.
   * Busca un usuario por correo electrónico. Si lo encuentra, devuelve los datos del usuario.
   * Si no lo encuentra, delega la solicitud al siguiente manejador.
   * @param request La solicitud de inicio de sesión social que contiene el correo electrónico del usuario.
   * @return Un {@link UserResponseDto} con la información del usuario si se encuentra, o el resultado del siguiente manejador.
   */
  @Override
  public UserResponseDto handleRequest(SocialSigninRequestDto request) {
    List<User> userFoundByEmail = Main.db.userDao().findOneByEmail(request.getEmail());

    if(userFoundByEmail.isEmpty()) {
      return this.nextHandler.handleRequest(request);
    }

    User userFound = userFoundByEmail.get(0);

    return UserResponseDto.builder()
            .id(userFound.getId())
            .email(userFound.getEmail())
            .name(userFound.getName())
            .build();
  }

  /**
   * Establece el siguiente manejador en la cadena de responsabilidad.
   * @param nextHandler El siguiente manejador a establecer.
   */
  @Override
  public void setNextHandler(IHandler<SocialSigninRequestDto> nextHandler) {
    this.nextHandler = nextHandler;
  }
}
