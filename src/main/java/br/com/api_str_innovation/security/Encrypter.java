package br.com.api_str_innovation.security;

import lombok.Setter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {

    private MessageDigest messageDigest;
    private static int BITMASK = 0xff;

    public String encrypt(String textPlain) {
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

    private String toHexadecimal(byte[] hash) {
        var hexString = new StringBuilder();
        for (byte bit : hash) {
            var hex = Integer.toHexString(BITMASK & bit);
            if (hex.length() == 1) {
                hexString.append("0");
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
