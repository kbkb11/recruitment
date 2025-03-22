package com.recruitment.controller;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.JobQuestion;
import com.recruitment.service.JobQuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-questions")
public class JobQuestionController {

    private final JobQuestionService jobQuestionService;

    public JobQuestionController(JobQuestionService jobQuestionService) {
        this.jobQuestionService = jobQuestionService;
    }

    @PostMapping("/send")
    public Result sendMessage(
            @RequestParam Long userId,
            @RequestParam Long jobId,
            @RequestParam String content) {
        try {
            boolean success = jobQuestionService.sendMessage(userId, jobId, content);
            return success ?
                    Result.success("消息发送成功") :
                    Result.failure("消息发送失败");
        } catch (Exception e) {
            return Result.failure("发送消息出错: " + e.getMessage());
        }
    }

    @GetMapping("/job/{jobId}")
    public Result getConversationHistory(@PathVariable Long jobId) {
        try {
            List<JobQuestion> history = jobQuestionService.getConversationHistory(jobId);
            return Result.success(history);
        } catch (Exception e) {
            return Result.failure("获取对话历史失败: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/job/{jobId}")
    public Result getUserJobConversation(
            @PathVariable Long userId,
            @PathVariable Long jobId) {
        try {
            List<JobQuestion> conversation = jobQuestionService.getUserJobConversation(userId, jobId);
            return Result.success(conversation);
        } catch (Exception e) {
            return Result.failure("获取用户对话历史失败: " + e.getMessage());
        }
    }
} 