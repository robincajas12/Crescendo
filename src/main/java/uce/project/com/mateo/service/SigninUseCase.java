package uce.project.com.mateo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uce.project.com.Main;
import uce.project.com.condor.User;
import uce.project.com.mateo.shared.dto.UserResponseDto;
import uce.project.com.mateo.utils.Encrypter;

import java.util.List;

@Service
public class SigninUseCase {

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
}
