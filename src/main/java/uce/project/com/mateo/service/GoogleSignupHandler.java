package uce.project.com.mateo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uce.project.com.Main;
import uce.project.com.condor.User;
import uce.project.com.mateo.shared.dto.SocialSigninRequestDto;
import uce.project.com.mateo.shared.dto.UserResponseDto;
import uce.project.com.mateo.shared.services.IHandler;

@Service
public class GoogleSignupHandler implements IHandler<SocialSigninRequestDto> {
  private IHandler<SocialSigninRequestDto> nextHandler;
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

  @Override
  public void setNextHandler(IHandler<SocialSigninRequestDto> nextHandler) {
    this.nextHandler = null;
  }
}
