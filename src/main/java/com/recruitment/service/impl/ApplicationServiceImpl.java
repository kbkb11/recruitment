package com.recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recruitment.entity.DO.Application;
import com.recruitment.mapper.ApplicationMapper;
import com.recruitment.service.ApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    /**
     * 根据职位 ID 获取申请记录列表
     *
     * @param jobId 职位 ID
     * @return 符合条件的申请记录列表
     */
    @Override
    public List<Application> getApplicationsByJobId(Long jobId) {
        QueryWrapper<Application> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_id", jobId);
        return list(queryWrapper);
    }

    /**
     * 更新单个申请记录的状态
     *
     * @param applicationId 申请记录 ID
     * @param status        新的申请状态
     * @return 更新是否成功
     */
    @Override
    @Transactional
    public boolean updateApplicationStatus(Long applicationId, Application.ApplicationStatus status) {
        Application application = getById(applicationId);
        if (application == null) {
            return false;
        }
        application.setStatus(status);
        return updateById(application);
    }

    /**
     * 批量更新申请记录的状态
     *
     * @param applicationIds 待更新申请记录 ID 列表
     * @param status         新的申请状态
     * @return 更新是否成功
     */
    @Override
    @Transactional
    public boolean batchUpdateStatus(List<Long> applicationIds, Application.ApplicationStatus status) {
        return lambdaUpdate()
                .in(Application::getId, applicationIds)
                .set(Application::getStatus, status)
                .update();
    }

    /**
     * 获取单个申请记录的详细信息
     *
     * @param applicationId 申请记录 ID
     * @return 对应申请记录的详细信息
     */
    @Override
    public Application getApplicationDetail(Long applicationId) {
        return getById(applicationId);
    }

    /**
     * 创建新的职位申请
     *
     * @param application 申请信息
     * @return 创建是否成功
     */
    @Override
    @Transactional
    public boolean createApplication(Application application) {
        // 设置初始状态为待处理
        application.setStatus(Application.ApplicationStatus.PENDING);
        return save(application);
    }
}
