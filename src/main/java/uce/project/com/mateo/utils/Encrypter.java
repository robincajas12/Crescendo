package uce.project.com.mateo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class Encrypter {
  public static String hash(String input) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);

      result.append(String.format("%02x", (int)c + i));
    }

    return result.toString();
  }

  public static boolean verificar(String input, String hashEsperado) {
    return hash(input).equals(hashEsperado);
  }
}
