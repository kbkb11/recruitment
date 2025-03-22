package com.recruitment.controller;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.EnterpriseUser;
import com.recruitment.service.EnterpriseUserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/enterprises")
public class EnterpriseUserController {
    private final EnterpriseUserService enterpriseUserService;

    public EnterpriseUserController(EnterpriseUserService enterpriseUserService) {
        this.enterpriseUserService = enterpriseUserService;
    }

    // 关键字搜索公司
    @GetMapping("/search")
    public Result searchEnterprises(@RequestParam String keyword) {
        try {
            List<EnterpriseUser> enterprises = enterpriseUserService.searchByKeyword(keyword);
            return Result.success(enterprises);
        } catch (Exception e) {
            return Result.failure("搜索公司失败: " + e.getMessage());
        }
    }
} 