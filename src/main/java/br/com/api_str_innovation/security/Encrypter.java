package br.com.api_str_innovation.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



    private static String toHexadecimal(byte[] hash) {
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
