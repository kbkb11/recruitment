package com.recruitment.controller;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.Application;
import com.recruitment.entity.DO.ApplicationStage;
import com.recruitment.service.ApplicationService;
import com.recruitment.service.ApplicationStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    private final ApplicationStageService stageService;

    public ApplicationController(ApplicationService applicationService, ApplicationStageService stageService) {
        this.applicationService = applicationService;
        this.stageService = stageService;
    }

    @PostMapping("/apply")
    public Result applyForJob(@RequestBody Application application) {
        try {
            boolean success = applicationService.createApplication(application);
            return success ?
                    Result.success("申请职位成功") :
                    Result.failure("申请职位失败");
        } catch (Exception e) {
            return Result.failure("申请职位出错: " + e.getMessage());
        }
    }

    @GetMapping("/job/{jobId}")
    public Result getJobApplications(@PathVariable Long jobId) {
        try {
            List<Application> applications = applicationService.getApplicationsByJobId(jobId);
            return Result.success(applications);
        } catch (Exception e) {
            return Result.failure("获取职位申请列表失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public Result updateStatus(
            @PathVariable Long id,
            @RequestParam Application.ApplicationStatus status) {
        try {
            boolean success = applicationService.updateApplicationStatus(id, status);
            return success ?
                    Result.success("更新申请状态成功") :
                    Result.failure("更新申请状态失败");
        } catch (Exception e) {
            return Result.failure("更新申请状态出错: " + e.getMessage());
        }
    }

    @PutMapping("/batchStatus")
    public Result batchUpdateStatus(
            @RequestParam List<Long> ids,
            @RequestParam Application.ApplicationStatus status) {
        try {
            boolean success = applicationService.batchUpdateStatus(ids, status);
            return success ?
                    Result.success("批量更新申请状态成功") :
                    Result.failure("批量更新申请状态失败");
        } catch (Exception e) {
            return Result.failure("批量更新申请状态出错: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getApplicationDetail(@PathVariable Long id) {
        try {
            Application application = applicationService.getApplicationDetail(id);
            return application != null ?
                    Result.success(application) :
                    Result.failure("未找到对应的申请记录");
        } catch (Exception e) {
            return Result.failure("获取申请详情失败: " + e.getMessage());
        }
    }

    @PostMapping
    public Result createStage(@RequestBody ApplicationStage stage) {
        try {
            boolean success = stageService.createStage(stage);
            return success ?
                    Result.success("创建招聘阶段成功") :
                    Result.failure("创建招聘阶段失败");
        } catch (Exception e) {
            return Result.failure("创建招聘阶段出错: " + e.getMessage());
        }
    }

    @GetMapping("/application/{applicationId}")
    public Result getStages(@PathVariable Long applicationId) {
        try {
            List<ApplicationStage> stages = stageService.getStagesByApplicationId(applicationId);
            return Result.success(stages);
        } catch (Exception e) {
            return Result.failure("获取招聘阶段列表失败: " + e.getMessage());
        }
    }

    @PutMapping("/application/status/{id}")
    public Result updateStageStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStage.StageStatus status,
            @RequestParam(required = false) String remarks) {
        try {
            boolean success = stageService.updateStageStatus(id, status, remarks);
            return success ?
                    Result.success("更新阶段状态成功") :
                    Result.failure("更新阶段状态失败");
        } catch (Exception e) {
            return Result.failure("更新阶段状态出错: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getStageDetail(@PathVariable Long id) {
        try {
            ApplicationStage stage = stageService.getStageDetail(id);
            return stage != null ?
                    Result.success(stage) :
                    Result.failure("未找到对应的招聘阶段");
        } catch (Exception e) {
            return Result.failure("获取招聘阶段详情失败: " + e.getMessage());
        }
    }

    @PutMapping("/applicationStage/batchStatus")
    public Result batchUpdateStages(
            @RequestParam List<Long> ids,
            @RequestParam ApplicationStage.StageStatus status) {
        try {
            boolean success = stageService.batchUpdateStages(ids, status);
            return success ?
                    Result.success("批量更新阶段状态成功") :
                    Result.failure("批量更新阶段状态失败");
        } catch (Exception e) {
            return Result.failure("批量更新阶段状态出错: " + e.getMessage());
        }
    }
}