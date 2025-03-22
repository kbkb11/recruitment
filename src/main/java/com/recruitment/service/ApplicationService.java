package com.recruitment.service;

import com.recruitment.entity.DO.Application;
import java.util.List;

public interface ApplicationService {
    // 获取企业收到的所有简历
    List<Application> getApplicationsByJobId(Long jobId);

    // 更新简历状态
    boolean updateApplicationStatus(Long applicationId, Application.ApplicationStatus status);

    // 批量更新简历状态
    boolean batchUpdateStatus(List<Long> applicationIds, Application.ApplicationStatus status);

    // 获取简历详细信息
    Application getApplicationDetail(Long applicationId);

    // 创建新的职位申请
    boolean createApplication(Application application);
}