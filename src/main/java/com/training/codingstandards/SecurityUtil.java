package com.training.codingstandards;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public class SecurityUtil {

    private static final String API_KEY = System.getenv().getOrDefault("APP_API_KEY", "demo-api-key");
    private static final String ADMIN_PASSWORD = System.getenv().getOrDefault("APP_ADMIN_PASSWORD", "demo-password");

    public static String hashIdentifier(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte element : digest) {
                sb.append(String.format("%02x", element));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is required for hashing", e);
        }
    }

    public static String sessionToken() {
        byte[] randomBytes = new byte[16];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes) + "-demo";
    }

    public static boolean isAdmin(String password) {
        return Objects.equals(password, ADMIN_PASSWORD);
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getAdminPassword() {
        return ADMIN_PASSWORD;
    }
}
