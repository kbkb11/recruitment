package com.recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.recruitment.entity.DO.Resume;
import com.recruitment.mapper.ResumeMapper;
import com.recruitment.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    @Transactional
    public Resume saveOrUpdateResume(Resume resume) {
        // 设置更新时间
        resume.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        
        if (resume.getId() == null) {
            // 新增简历
            resume.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            resumeMapper.insert(resume);
        } else {
            // 更新简历
            resumeMapper.updateById(resume);
        }
        return resume;
    }

    @Override
    public Resume getResumeByUserId(Long userId) {
        LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Resume::getUserId, userId);
        return resumeMapper.selectOne(queryWrapper);
    }
} 