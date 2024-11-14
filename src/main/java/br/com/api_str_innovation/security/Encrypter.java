package br.com.api_str_innovation.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {

    private static MessageDigest messageDigest;
    private static int BITMASK = 0xff;

    public static String encrypt(String textPlain) {
        try {
            if (messageDigest == null) {
                messageDigest = MessageDigest.getInstance("sha-512");
            }
            var hash = messageDigest.digest(textPlain.getBytes());
            return toHexadecimal(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Erro de criptografia");
        }
    }


}
