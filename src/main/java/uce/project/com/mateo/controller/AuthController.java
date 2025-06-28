package uce.project.com.mateo.controller;

import org.springframework.web.bind.annotation.*;
import uce.project.com.mateo.service.SigninUseCase;
import uce.project.com.mateo.service.SignupUseCase;
import uce.project.com.mateo.shared.dto.CreateUserDto;
import uce.project.com.mateo.shared.dto.SigninRequestDto;
import uce.project.com.mateo.shared.dto.UserResponseDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final SignupUseCase _signupUseCase;
  private final SigninUseCase _signinUseCase;

  public AuthController(SignupUseCase signupUseCase, SigninUseCase signinUseCase ) {
    this._signupUseCase = signupUseCase;
    this._signinUseCase = signinUseCase;

  }

  @PostMapping("/signup")
  public UserResponseDto signup(@RequestBody CreateUserDto createUserDto) {
    return this._signupUseCase.createUser( createUserDto );
  }

  @PostMapping("/signin")
  public UserResponseDto signin(@RequestBody SigninRequestDto signinRequestDto ) {
    return this._signinUseCase.signin(signinRequestDto.getEmail(), signinRequestDto.getPassword());
  }
}