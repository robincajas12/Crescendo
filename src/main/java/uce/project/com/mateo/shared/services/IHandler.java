package uce.project.com.mateo.shared.services;

import uce.project.com.mateo.shared.dto.UserResponseDto;

public interface IHandler<T> {
  UserResponseDto handleRequest(T request);

  void setNextHandler(IHandler<T> nextHandler);
}
