package com.recruitment.service;

import com.recruitment.entity.DO.EnterpriseUser;
import java.util.List;

public interface EnterpriseUserService {
    // 关键字搜索
    List<EnterpriseUser> searchByKeyword(String keyword);
} 