package com.recruitment.controller; // 建议的包名

import com.recruitment.common.Result;
import com.recruitment.entity.DO.Job;
import com.recruitment.service.JobService; // 引入Service层
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // 处理职位请求保存草稿
    @PostMapping("/draft")
    public Result draftJob(@RequestBody String title,
                           @RequestBody String description,
                           @RequestBody String requirements,
                           @RequestBody String salaryRange,
                           @RequestBody String location,
                           @RequestBody Job.JobStatus status,
                           @RequestBody Long enterpriseId,
                           @RequestBody Long jobId) {
        Result result;
        if(jobId == null){
            result = jobService.createJob(title, description, requirements, salaryRange, location, status, enterpriseId);
        }else{
            result = jobService.updateJob(title, description, requirements, salaryRange, location, status, enterpriseId);
        }
        return result;
    }

    // 处理发布职位请求
    @PostMapping("/publish")
    public Result publishJob(@RequestBody String title,
                             @RequestBody String description,
                             @RequestBody String requirements,
                             @RequestBody String salaryRange,
                             @RequestBody String location,
                             @RequestBody Job.JobStatus status,
                             @RequestBody Long enterpriseId,
                             @RequestBody Long jobId) {
        Result result;
        if(jobId == null){
            result = jobService.createJob(title, description, requirements, salaryRange, location, status, enterpriseId);
        }else{
            result = jobService.updateJob(title, description, requirements, salaryRange, location, status, enterpriseId);
        }
        return result;
    }

    // 处理更新职位请求
    @PostMapping("/update")
    public Result updateJob(@RequestBody String title,
                            @RequestBody String description,
                            @RequestBody String requirements,
                            @RequestBody String salaryRange,
                            @RequestBody String location,
                            @RequestBody Job.JobStatus status,
                            @RequestBody Long enterpriseId,
                            @PathVariable Long id) {
        Result result = jobService.updateJob(title, description, requirements, salaryRange, location, status, enterpriseId);
        return result;
    }

    // 处理删除职位请求
    @GetMapping("/delete") //通常删除使用delete请求，这里为了演示方便
    public Result deleteJob(@PathVariable Long id) {
        Result result = jobService.deleteJob(id);
        return result;
    }
}