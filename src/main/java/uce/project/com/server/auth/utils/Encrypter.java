package uce.project.com.server.auth.utils;

/**
 * Clase de utilidad para la encriptación y verificación de cadenas de texto.
 * Proporciona métodos para "hashear" una cadena y para verificar si una cadena coincide con un hash dado.
 * NOTA: Este método de "hashing" es muy simple y no debe usarse para seguridad real en producción.
 * Es solo para propósitos de demostración o desarrollo ligero.
 */
public class Encrypter {
  /**
   * Genera un "hash" simple de una cadena de entrada.
   * Este método suma el índice de cada carácter a su valor ASCII y lo convierte a hexadecimal.
   * @param input La cadena de texto a "hashear".
   * @return Una cadena que representa el "hash" de la entrada.
   */
  public static String hash(String input) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);

      result.append(String.format("%02x", (int)c + i));
    }

    return result.toString();
  }

  /**
   * Verifica si una cadena de entrada coincide con un "hash" esperado.
   * Para ello, "hashea" la cadena de entrada y la compara con el "hash" esperado.
   * @param input La cadena de texto a verificar.
   * @param inputExpected El "hash" esperado con el que comparar.
   * @return `true` si el "hash" de la entrada coincide con el "hash" esperado, `false` en caso contrario.
   */
  public static boolean verify(String input, String inputExpected) {
    return hash(input).equals(inputExpected);
  }
}
