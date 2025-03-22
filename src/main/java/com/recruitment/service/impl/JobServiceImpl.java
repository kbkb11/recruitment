package com.recruitment.service.impl;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.Job;
import com.recruitment.mapper.EnterpriseUserMapper;
import com.recruitment.mapper.JobMapper; // 使用 JobMapper
import com.recruitment.service.JobService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;

@Service
public class JobServiceImpl implements JobService {
    private static final Logger log = LogManager.getLogger(JobServiceImpl.class);
    private final JobMapper jobMapper; // 使用 JobMapper
    private final EnterpriseUserMapper enterpriseUserMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public JobServiceImpl(JobMapper jobMapper, EnterpriseUserMapper enterpriseUserMapper, RedisTemplate<String, Object> redisTemplate) {
        this.jobMapper = jobMapper;
        this.enterpriseUserMapper = enterpriseUserMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional
    public Result createJob(String title, String description, String requirements, String salaryRange, String location, Job.JobStatus status, Long enterpriseId){
        Job job = Job.builder()
                .title(title)
                .description(description)
                .requirements(requirements)
                .salaryRange(salaryRange)
                .location(location)
                .status(status)
                .enterpriseId(enterpriseId)
                .build();

        job.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        jobMapper.insert(job); // 执行插入操作
        return Result.success(job.getId());
    }

    @Override
    @Transactional
    public Result updateJob(String title, String description, String requirements, String salaryRange, String location, Job.JobStatus status, Long enterpriseId){
        Job job = Job.builder()
                .title(title)
                .description(description)
                .requirements(requirements)
                .salaryRange(salaryRange)
                .location(location)
                .status(status)
                .enterpriseId(enterpriseId)
                .build();

        job.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        jobMapper.updateById(job); // 使用 updateById
        return Result.success(job.getId());
    }


    @Override
    public List<Job> getJobsByEnterpriseId(Long enterpriseId) {
        return jobMapper.selectByEnterpriseId(enterpriseId); // 使用 selectByEnterpriseId
    }

    @Override
    public List<Job> getAllJobs() {
        return jobMapper.selectAll();
    }

    @Override
    @Transactional
    public Result deleteJob(Long id) {
        Job job = jobMapper.selectById(id);
        if(job == null) {
            return Result.failure("Job not found");
        }
        jobMapper.deleteById(id);
        return Result.success("Job deleted");
    }

    @Override
    public List<Job> getJobsByStatus(Job.JobStatus status) {
        return jobMapper.selectByStatus(status); // 使用 selectByStatus
    }

    @Override
    public List<Job> getJobsByEnterpriseIdAndStatus(Long enterpriseId, Job.JobStatus status) {
        return jobMapper.selectByEnterpriseIdAndStatus(enterpriseId, status); // 使用 selectByEnterpriseIdAndStatus
    }

    @Override
    public List<Job> searchByKeyword(String keyword) {
        return jobMapper.searchByKeyword(keyword); // 使用 searchByKeyword
    }

}