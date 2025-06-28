package uce.project.com.mateo.shared.services;

public interface IHandler<T> {
  void handleRequest(T request);

  void setNextHandler(IHandler<T> nextHandler);
}
