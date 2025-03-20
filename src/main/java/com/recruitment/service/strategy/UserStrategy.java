package com.recruitment.service.strategy;

public interface UserStrategy {
    void register(String username, String password, String phoneNumber, Object additionalInfo);
    String login(String username, String password);
}
