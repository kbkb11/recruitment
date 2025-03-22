package com.recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recruitment.entity.DO.JobQuestion;
import com.recruitment.mapper.JobQuestionMapper;
import com.recruitment.service.JobQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class JobQuestionServiceImpl extends ServiceImpl<JobQuestionMapper, JobQuestion> implements JobQuestionService {

    @Override
    @Transactional
    public boolean sendMessage(Long userId, Long jobId, String content) {
        JobQuestion question = JobQuestion.builder()
                .userId(userId)
                .jobId(jobId)
                .content(content)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();
        return save(question);
    }

    @Override
    public List<JobQuestion> getConversationHistory(Long jobId) {
        LambdaQueryWrapper<JobQuestion> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(JobQuestion::getJobId, jobId)
                .orderByAsc(JobQuestion::getCreatedAt);
        return list(wrapper);
    }

    @Override
    public List<JobQuestion> getUserJobConversation(Long userId, Long jobId) {
        LambdaQueryWrapper<JobQuestion> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(JobQuestion::getUserId, userId)
                .eq(JobQuestion::getJobId, jobId)
                .orderByAsc(JobQuestion::getCreatedAt);
        return list(wrapper);
    }
} 