package uce.project.com.mateo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uce.project.com.Main;
import uce.project.com.condor.User;
import uce.project.com.mateo.shared.dto.SocialSigninRequestDto;
import uce.project.com.mateo.shared.dto.UserResponseDto;
import uce.project.com.mateo.utils.Encrypter;

import java.util.List;

/**
 * Caso de uso para el inicio de sesión de usuarios, incluyendo inicio de sesión tradicional y social.
 * Gestiona la lógica de autenticación y la integración con manejadores de inicio de sesión social.
 */
@Service
public class SigninUseCase {

  /**
   * Manejador para el inicio de sesión a través de Google.
   */
  private final GoogleSigninHandler _googleSigninHandler;
  /**
   * Manejador para el registro a través de Google.
   */
  private final GoogleSignupHandler _googleSignupHandler;

  /**
   * Constructor del caso de uso de inicio de sesión.
   * Configura la cadena de responsabilidad para el inicio de sesión social.
   * @param googleSigninHandler El manejador para el inicio de sesión de Google.
   * @param googleSignupHandler El manejador para el registro de Google.
   */
  public SigninUseCase(GoogleSigninHandler googleSigninHandler, GoogleSignupHandler googleSignupHandler) {
    this._googleSigninHandler = googleSigninHandler;
    this._googleSignupHandler = googleSignupHandler;

    // Establece la cadena de responsabilidad: si GoogleSigninHandler no encuentra el usuario, pasa a GoogleSignupHandler.
    this._googleSigninHandler.setNextHandler(this._googleSignupHandler);
  }

  /**
   * Realiza el inicio de sesión de un usuario con correo electrónico y contraseña.
   * @param email El correo electrónico del usuario.
   * @param password La contraseña del usuario.
   * @return Un {@link UserResponseDto} con la información del usuario si las credenciales son válidas.
   * @throws ResponseStatusException Si el correo electrónico o la contraseña son nulos/vacíos, el correo no se encuentra, o la contraseña es inválida.
   */
  public UserResponseDto signin(String email, String password) {

    if(email == null || email.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
    }

    if(password == null || password.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null or empty");
    }

    List<User> userFoundByEmail = Main.db.userDao().findOneByEmail(email);

    if(userFoundByEmail.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email not found");
    }

    User userFound = userFoundByEmail.get(0);

    String passwordFromUserFound = userFound.getPassword();

    boolean isPasswordValid = Encrypter.verify(password, passwordFromUserFound);

    if(!isPasswordValid) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
    }

    return UserResponseDto.builder()
        .id(userFound.getId())
        .email(userFound.getEmail())
        .name(userFound.getName())
        .build();
  }

  /**
   * Realiza el inicio de sesión social de un usuario.
   * Delega la solicitud al manejador de inicio de sesión de Google, que a su vez puede pasarla al manejador de registro.
   * @param socialSigninRequestDto Objeto que contiene la información para el inicio de sesión social (email, nombre).
   * @return Un {@link UserResponseDto} con la información del usuario que ha iniciado sesión socialmente.
   * @throws ResponseStatusException Si el correo electrónico o el nombre son nulos/vacíos en la solicitud social.
   */
  public UserResponseDto socialSignin(SocialSigninRequestDto socialSigninRequestDto) {

    if(socialSigninRequestDto.getEmail() == null || socialSigninRequestDto.getEmail().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
    }

    if(socialSigninRequestDto.getName() == null || socialSigninRequestDto.getName().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
    }

    return this._googleSigninHandler.handleRequest(socialSigninRequestDto);
  }
}
