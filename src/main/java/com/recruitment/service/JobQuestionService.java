package com.recruitment.service;

import com.recruitment.entity.DO.JobQuestion;
import java.util.List;

public interface JobQuestionService {
    // 发送消息
    boolean sendMessage(Long userId, Long jobId, String content);
    
    // 获取某个职位的所有对话历史
    List<JobQuestion> getConversationHistory(Long jobId);
    
    // 获取某个用户和某个职位的对话历史
    List<JobQuestion> getUserJobConversation(Long userId, Long jobId);
} 