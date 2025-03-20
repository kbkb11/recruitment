package com.recruitment.service.impl;

import com.recruitment.common.Result;
import com.recruitment.service.LoginAndRegisterService;
import com.recruitment.service.strategy.UserStrategy;
import com.recruitment.service.strategy.impl.EnterpriseUserStrategy;
import com.recruitment.service.strategy.impl.NormalUserStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {
    private static final Logger log = LogManager.getLogger(LoginAndRegisterServiceImpl.class);
    private final EnterpriseUserStrategy enterpriseUserStrategy;
    private final NormalUserStrategy normalUserRegistrationStrategy;

    public LoginAndRegisterServiceImpl(EnterpriseUserStrategy enterpriseUserStrategy, NormalUserStrategy normalUserRegistrationStrategy) {
        this.enterpriseUserStrategy = enterpriseUserStrategy;
        this.normalUserRegistrationStrategy = normalUserRegistrationStrategy;
    }

    @Override
    public Result register(String username, String password, String phoneNumber, Object additionalInfo, String userType) {
        UserStrategy userStrategy = null;

        if(userType.equals("normalUser")){
            userStrategy = normalUserRegistrationStrategy;
        }else if(userType.equals("enterpriseUser")) {
            userStrategy = enterpriseUserStrategy;
        }

        if (userStrategy != null) {
            userStrategy.register(username, password, phoneNumber, additionalInfo);
        } else {
            log.error("Invalid user type.");
            return Result.failure("Invalid user type.");
        }

        return Result.success("Registration successful.");
    }

    @Override
    public Result login(String username, String password, String userType) {
        UserStrategy userStrategy = null;

        if(userType.equals("normalUser")){
            userStrategy = normalUserRegistrationStrategy;
        }else if(userType.equals("enterpriseUser")) {
            userStrategy = enterpriseUserStrategy;
        }

        String token = null;
        if (userStrategy != null) {
            token = userStrategy.login(username, password);
        } else {
            log.error("Invalid user type.");
            return Result.failure("Invalid user type.");
        }

        return Result.success(token);
    }
}
