package com.recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recruitment.entity.DO.ApplicationStage;
import com.recruitment.mapper.ApplicationStageMapper;
import com.recruitment.service.ApplicationStageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ApplicationStageServiceImpl extends ServiceImpl<ApplicationStageMapper, ApplicationStage>
        implements ApplicationStageService {

    @Override
    @Transactional
    public boolean createStage(ApplicationStage stage) {
        return save(stage);
    }

    @Override
    public List<ApplicationStage> getStagesByApplicationId(Long applicationId) {
        LambdaQueryWrapper<ApplicationStage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApplicationStage::getApplicationId, applicationId)
                .orderByAsc(ApplicationStage::getStageName);
        return list(wrapper);
    }

    @Override
    @Transactional
    public boolean updateStageStatus(Long stageId, ApplicationStage.StageStatus status, String remarks) {
        ApplicationStage stage = getById(stageId);
        if (stage == null) {
            return false;
        }
        stage.setStatus(status);
        stage.setRemarks(remarks);
        stage.setEndTime(new Timestamp(System.currentTimeMillis()));
        return updateById(stage);
    }

    @Override
    public ApplicationStage getStageDetail(Long stageId) {
        return getById(stageId);
    }

    @Override
    @Transactional
    public boolean batchUpdateStages(List<Long> stageIds, ApplicationStage.StageStatus status) {
        return lambdaUpdate()
                .in(ApplicationStage::getId, stageIds)
                .set(ApplicationStage::getStatus, status)
                .set(ApplicationStage::getEndTime, new Timestamp(System.currentTimeMillis()))
                .update();
    }
}