package com.recruitment.controller;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.RecruitmentAd;
import com.recruitment.service.RecruitmentAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment-ad")
public class RecruitmentAdController {

    @Autowired
    private RecruitmentAdService recruitmentAdService;

    @PostMapping("/create")
    public Result createAd(@RequestBody RecruitmentAd recruitmentAd) {
        return Result.success(recruitmentAdService.createAd(recruitmentAd));
    }

    @GetMapping("/list")
    public Result listAds(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long enterpriseId) {
        return Result.success(recruitmentAdService.listAds(status, enterpriseId));
    }

    @PutMapping("/update-status/{id}")
    public Result updateAdStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        return Result.success(recruitmentAdService.updateAdStatus(id, status));
    }
} 