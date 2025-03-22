package com.recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recruitment.entity.DO.RecruitmentAd;
import com.recruitment.mapper.RecruitmentAdMapper;
import com.recruitment.service.RecruitmentAdService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.sql.Timestamp;

@Service
public class RecruitmentAdServiceImpl extends ServiceImpl<RecruitmentAdMapper, RecruitmentAd> implements RecruitmentAdService {

    @Override
    public RecruitmentAd createAd(RecruitmentAd recruitmentAd) {
        // 设置初始状态为待审核
        recruitmentAd.setStatus(0);
        recruitmentAd.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        recruitmentAd.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        save(recruitmentAd);
        return recruitmentAd;
    }

    @Override
    public List<RecruitmentAd> listAds(Integer status, Long enterpriseId) {
        LambdaQueryWrapper<RecruitmentAd> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(RecruitmentAd::getStatus, status);
        }
        if (enterpriseId != null) {
            wrapper.eq(RecruitmentAd::getEnterpriseId, enterpriseId);
        }
        wrapper.orderByDesc(RecruitmentAd::getCreatedAt);
        return list(wrapper);
    }

    @Override
    public Boolean updateAdStatus(Long id, Integer status) {
        RecruitmentAd ad = getById(id);
        if (ad == null) {
            return false;
        }
        ad.setStatus(status);
        ad.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return updateById(ad);
    }
} 