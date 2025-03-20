package com.recruitment.utils;

import java.util.UUID;

public class TokenUtil {

    /**
     * 生成 Token (使用 UUID)
     */
    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        String token = generateToken();
        System.out.println("生成的 Token：" + token);
    }
}
