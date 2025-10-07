package com.app.core.utils;

import java.security.MessageDigest;
import java.util.Base64;

public class PasswordUtils {

    public static String encryptPassword(final String password) {
        try {

            final MessageDigest digest = MessageDigest.getInstance("SHA-256");

            final byte[] hash = digest.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean matches(final String rawPassword, final String storedHash) {
        final String newHash = encryptPassword(rawPassword);

        return newHash.equals(storedHash);
    }

}
