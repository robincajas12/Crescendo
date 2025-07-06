package uce.project.com.baraja;

/**
 * Interfaz genérica para realizar preguntas o solicitudes.
 * Define métodos para interactuar con un sistema y obtener una respuesta de tipo T.
 * @param <T> El tipo de respuesta esperada.
 */
public interface IAsk<T> {
    /**
     * Realiza una pregunta o solicitud simple.
     * @param promt El texto de la pregunta o solicitud.
     * @return La respuesta de tipo T.
     */
    T ask(String promt);

    /**
     * Realiza una pregunta o solicitud con instrucciones adicionales.
     * @param promt El texto de la pregunta o solicitud.
     * @param instructions Instrucciones adicionales para la solicitud.
     * @return La respuesta de tipo T.
     */
    T ask(String promt, String instructions);
}
