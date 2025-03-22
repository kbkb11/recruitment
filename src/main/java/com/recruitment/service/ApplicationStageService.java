package com.recruitment.service;

import com.recruitment.entity.DO.ApplicationStage;
import java.util.List;

public interface ApplicationStageService {
    // 创建新的招聘阶段
    boolean createStage(ApplicationStage stage);

    // 获取某个申请的所有阶段
    List<ApplicationStage> getStagesByApplicationId(Long applicationId);

    // 更新阶段状态
    boolean updateStageStatus(Long stageId, ApplicationStage.StageStatus status, String remarks);

    // 获取阶段详情
    ApplicationStage getStageDetail(Long stageId);

    // 批量更新阶段状态
    boolean batchUpdateStages(List<Long> stageIds, ApplicationStage.StageStatus status);
}