package de.excellence.util;

import java.security.SecureRandom;

public class TokenUtil {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String randomToken(int tokenLength) {
        StringBuilder sb = new StringBuilder(tokenLength);
        for (int i = 0; i < tokenLength; i++)
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        return sb.toString();
    }
}
