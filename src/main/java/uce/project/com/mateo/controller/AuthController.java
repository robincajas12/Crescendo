package uce.project.com.mateo.controller;

import org.springframework.web.bind.annotation.*;
import uce.project.com.mateo.service.SignupUseCase;
import uce.project.com.mateo.shared.dto.CreateUserDto;
import uce.project.com.mateo.shared.dto.SigninRequestDto;
import uce.project.com.mateo.shared.dto.UserResponseDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final SignupUseCase _signupUseCase;

  public AuthController(SignupUseCase signupUseCase) {
    this._signupUseCase = signupUseCase;
  }

  @PostMapping("/signup")
  public UserResponseDto signup(@RequestBody CreateUserDto createUserDto) {
    return this._signupUseCase.createUser( createUserDto );
  }

  @PostMapping("/signin")
  public UserResponseDto signin(@RequestBody SigninRequestDto signinRequestDto ) {
    return null;
  }
}