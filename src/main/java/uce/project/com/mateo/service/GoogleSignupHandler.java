package uce.project.com.mateo.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import uce.project.com.mateo.shared.dto.SocialSigninRequestDto;
import uce.project.com.mateo.shared.services.IHandler;

public class GoogleSignupHandler implements IHandler<SocialSigninRequestDto> {
  private IHandler<SocialSigninRequestDto> nextHandler;
  @Override
  public void handleRequest(SocialSigninRequestDto request) {

  }

  @Override
  public void setNextHandler(IHandler<SocialSigninRequestDto> nextHandler) {
    this.nextHandler = null;
  }
}
