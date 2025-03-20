package com.recruitment.service;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.Job;

import java.util.List;

public interface JobService {
    Result createJob(String title, String description, String requirements, String salaryRange, String location, Job.JobStatus status, Long enterpriseId);
    Result updateJob(String title, String description, String requirements, String salaryRange, String location, Job.JobStatus status, Long enterpriseId);
    List<Job> getJobsByEnterpriseId(Long enterpriseId);
    List<Job> getAllJobs();
    Result deleteJob(Long id);
    List<Job> getJobsByStatus(Job.JobStatus status);
    List<Job> getJobsByEnterpriseIdAndStatus(Long enterpriseId, Job.JobStatus status);
}
