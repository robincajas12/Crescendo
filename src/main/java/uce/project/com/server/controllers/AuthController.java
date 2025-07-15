package uce.project.com.server.controllers;

import org.springframework.web.bind.annotation.*;
import uce.project.com.server.auth.service.SigninUseCase;
import uce.project.com.server.auth.service.SignupUseCase;
import uce.project.com.server.auth.shared.dto.CreateUserDto;
import uce.project.com.server.auth.shared.dto.SigninRequestDto;
import uce.project.com.server.auth.shared.dto.SocialSigninRequestDto;
import uce.project.com.server.auth.shared.dto.UserResponseDto;

/**
 * Controlador REST para la autenticación de usuarios.
 * Proporciona endpoints para el registro (signup) y el inicio de sesión (signin) de usuarios.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  /**
   * Caso de uso para el registro de nuevos usuarios.
   */
  private final SignupUseCase _signupUseCase;
  /**
   * Caso de uso para el inicio de sesión de usuarios.
   */
  private final SigninUseCase _signinUseCase;

  /**
   * Constructor del controlador de autenticación.
   * @param signupUseCase El caso de uso para el registro de usuarios.
   * @param signinUseCase El caso de uso para el inicio de sesión de usuarios.
   */
  public AuthController(SignupUseCase signupUseCase, SigninUseCase signinUseCase ) {
    this._signupUseCase = signupUseCase;
    this._signinUseCase = signinUseCase;
  }

  /**
   * Endpoint para el registro de un nuevo usuario.
   * @param createUserDto Objeto que contiene la información para crear un nuevo usuario.
   * @return Un objeto UserResponseDto con la información del usuario registrado.
   */
  @PostMapping("/signup")
  public UserResponseDto signup(@RequestBody CreateUserDto createUserDto) {
    return this._signupUseCase.createUser( createUserDto );
  }

  /**
   * Endpoint para el inicio de sesión de un usuario con credenciales de correo electrónico y contraseña.
   * @param signinRequestDto Objeto que contiene el correo electrónico y la contraseña del usuario.
   * @return Un objeto UserResponseDto con la información del usuario que ha iniciado sesión.
   */
  @PostMapping("/signin")
  public UserResponseDto signin(@RequestBody SigninRequestDto signinRequestDto ) {
    return this._signinUseCase.signin(signinRequestDto.getEmail(), signinRequestDto.getPassword());
  }

  /**
   * Endpoint para el inicio de sesión social de un usuario.
   * @param socialSigninRequestDto Objeto que contiene la información para el inicio de sesión social.
   * @return Un objeto UserResponseDto con la información del usuario que ha iniciado sesión socialmente.
   */
  @PostMapping("/social/signin")
  public UserResponseDto socialSignin(@RequestBody SocialSigninRequestDto socialSigninRequestDto) {
    return this._signinUseCase.socialSignin(socialSigninRequestDto);
  }
}