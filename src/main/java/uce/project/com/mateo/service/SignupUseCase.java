package uce.project.com.mateo.service;

import org.springframework.stereotype.Service;
import uce.project.com.mateo.shared.dto.CreateUserDto;
import uce.project.com.mateo.shared.dto.UserResponseDto;

@Service
public class SignupUseCase {
  public UserResponseDto createUser(CreateUserDto createUserDto) {
    
    return new UserResponseDto();
  }
}
