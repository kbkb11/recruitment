package com.recruitment.service;

import com.recruitment.common.Result;

public interface LoginAndRegisterService {
    Result register(String username, String password, String phoneNumber, Object additionalInfo, String userType);
    Result login(String username, String password, String userType);
}
