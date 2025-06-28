package uce.project.com.mateo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class Encrypter {
  public static int hash(String input) {
    int hash = 0;

    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);

      hash += (c * (i + 1));
    }

    return hash;
  }

  public static boolean verificar(String input, int hashEsperado) {
    return hash(input) == hashEsperado;
  }
}
