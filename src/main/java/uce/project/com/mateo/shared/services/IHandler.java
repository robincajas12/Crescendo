package uce.project.com.mateo.shared.services;

import uce.project.com.mateo.shared.dto.UserResponseDto;

/**
 * Interfaz genérica para implementar el patrón Chain of Responsibility (Cadena de Responsabilidad).
 * Define métodos para manejar una solicitud y para establecer el siguiente manejador en la cadena.
 * @param <T> El tipo de la solicitud que este manejador puede procesar.
 */
public interface IHandler<T> {
  /**
   * Procesa una solicitud dada.
   * Si el manejador puede procesar la solicitud, lo hace y devuelve un {@link UserResponseDto}.
   * Si no puede, pasa la solicitud al siguiente manejador en la cadena.
   * @param request La solicitud a procesar.
   * @return Un {@link UserResponseDto} que representa el resultado del manejo de la solicitud.
   */
  UserResponseDto handleRequest(T request);

  /**
   * Establece el siguiente manejador en la cadena de responsabilidad.
   * @param nextHandler El siguiente manejador en la cadena.
   */
  void setNextHandler(IHandler<T> nextHandler);
}
