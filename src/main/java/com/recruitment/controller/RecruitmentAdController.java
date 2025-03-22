package com.recruitment.controller;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.RecruitmentAd;
import com.recruitment.service.RecruitmentAdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment-ad")
@Api(tags = "招聘广告相关接口")
public class RecruitmentAdController {

    @Autowired
    private RecruitmentAdService recruitmentAdService;

    @PostMapping("/create")
    @ApiOperation("企业发布招聘广告")
    public Result<RecruitmentAd> createAd(@RequestBody RecruitmentAd recruitmentAd) {
        return Result.success(recruitmentAdService.createAd(recruitmentAd));
    }

    @GetMapping("/list")
    @ApiOperation("获取所有招聘广告")
    public Result<List<RecruitmentAd>> listAds(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long enterpriseId) {
        return Result.success(recruitmentAdService.listAds(status, enterpriseId));
    }

    @PutMapping("/update-status/{id}")
    @ApiOperation("管理员更新广告状态")
    public Result<Boolean> updateAdStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        return Result.success(recruitmentAdService.updateAdStatus(id, status));
    }
} 