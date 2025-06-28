package uce.project.com.mateo.service;

import org.springframework.stereotype.Service;
import uce.project.com.Main;
import uce.project.com.condor.User;
import uce.project.com.mateo.shared.dto.SocialSigninRequestDto;
import uce.project.com.mateo.shared.dto.UserResponseDto;
import uce.project.com.mateo.shared.services.IHandler;

import java.util.List;

@Service
public class GoogleSigninHandler implements IHandler<SocialSigninRequestDto> {
  private IHandler<SocialSigninRequestDto> nextHandler;

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

  @Override
  public void setNextHandler(IHandler<SocialSigninRequestDto> nextHandler) {
    this.nextHandler = nextHandler;
  }
}
