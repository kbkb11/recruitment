package com.recruitment.service;

import com.recruitment.entity.DO.RecruitmentAd;
import java.util.List;

public interface RecruitmentAdService {
    RecruitmentAd createAd(RecruitmentAd recruitmentAd);
    List<RecruitmentAd> listAds(Integer status, Long enterpriseId);
    Boolean updateAdStatus(Long id, Integer status);
} 