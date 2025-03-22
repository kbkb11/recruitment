package com.recruitment.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {
    private static final String FIXED_SALT = "recruitment_system_2024";

    // 加密密码
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + FIXED_SALT;
            byte[] hash = md.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    // 验证密码
    public static boolean verify(String password, String hashedPassword) {
        String newHash = encrypt(password);
        return newHash.equals(hashedPassword);
    }
}