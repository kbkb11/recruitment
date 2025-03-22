package com.recruitment.controller;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.Resume;
import com.recruitment.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/save")
    public Result saveResume(@RequestBody Resume resume) {
        try {
            Resume savedResume = resumeService.saveOrUpdateResume(resume);
            return Result.success(savedResume);
        } catch (Exception e) {
            return Result.failure("保存简历失败：" + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public Result getResume(@PathVariable Long userId) {
        try {
            Resume resume = resumeService.getResumeByUserId(userId);
            if (resume != null) {
                return Result.success(resume);
            } else {
                return Result.failure(404, "未找到该用户的简历");
            }
        } catch (Exception e) {
            return Result.failure("获取简历失败：" + e.getMessage());
        }
    }
} 