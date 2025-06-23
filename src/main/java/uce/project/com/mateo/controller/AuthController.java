package uce.project.com.mateo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uce.project.com.mateo.shared.dto.CreateUserDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @GetMapping("/hello")
  public CreateUserDto hello(@RequestBody CreateUserDto createUserDto) {
    return createUserDto;
  }
}