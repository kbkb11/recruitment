package com.recruitment.controller;

import com.recruitment.common.Result;
import com.recruitment.service.LoginAndRegisterService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final LoginAndRegisterService loginAndRegisterService;

    public UserController(LoginAndRegisterService loginAndRegisterService) {
        this.loginAndRegisterService = loginAndRegisterService;
    }

    @PostMapping("/register/normal")
    public Result registerNormalUser(@RequestParam String username, @RequestParam String password,
                                     @RequestParam String phoneNumber, @RequestBody Map<String, Object> additionalInfo) {
        return loginAndRegisterService.register(username, password, phoneNumber, additionalInfo, "normalUser");
    }

    @PostMapping("/register/enterprise")
    public Result registerEnterpriseUser(@RequestParam String username, @RequestParam String password,
                                         @RequestParam String phoneNumber, @RequestBody Map<String, Object> additionalInfo) {
        return loginAndRegisterService.register(username, password, phoneNumber, additionalInfo, "enterpriseUser");
    }

    @PostMapping("/login/normal")
    public Result loginNormalUser(@RequestParam String username, @RequestParam String password) {
        return loginAndRegisterService.login(username, password, "normalUser");
    }

    @PostMapping("/login/enterprise")
    public Result loginEnterpriseUser(@RequestParam String username, @RequestParam String password) {
        return loginAndRegisterService.login(username, password, "enterpriseUser");
    }
}