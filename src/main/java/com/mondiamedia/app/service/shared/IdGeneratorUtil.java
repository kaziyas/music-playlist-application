package com.mondiamedia.app.service.shared;

import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public class IdGeneratorUtil {
  private static final Random random = new SecureRandom();
  private static final String ALPHABET =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  public static String generatePlaylistId(int length) {
    return generateRandomString(length);
  }

  private static String generateRandomString(int length) {
    StringBuilder returnValue = new StringBuilder(length);

    IntStream.range(0, length)
        .map(i -> ALPHABET.charAt(random.nextInt(ALPHABET.length())))
        .forEachOrdered(returnValue::append);
    return new String(returnValue);
  }
}
