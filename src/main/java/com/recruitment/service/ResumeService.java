package com.recruitment.service;

import com.recruitment.entity.DO.Resume;

public interface ResumeService {
    /**
     * 保存或更新简历
     * @param resume 简历信息
     * @return 保存后的简历
     */
    Resume saveOrUpdateResume(Resume resume);

    /**
     * 根据用户ID获取简历
     * @param userId 用户ID
     * @return 简历信息
     */
    Resume getResumeByUserId(Long userId);
} 