package com.recruitment.service.impl;

import com.recruitment.entity.DO.EnterpriseUser;
import com.recruitment.mapper.EnterpriseUserMapper;
import com.recruitment.service.EnterpriseUserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EnterpriseUserServiceImpl implements EnterpriseUserService {
    private final EnterpriseUserMapper enterpriseUserMapper;

    public EnterpriseUserServiceImpl(EnterpriseUserMapper enterpriseUserMapper) {
        this.enterpriseUserMapper = enterpriseUserMapper;
    }

    @Override
    public List<EnterpriseUser> searchByKeyword(String keyword) {
        return enterpriseUserMapper.searchByKeyword(keyword);
    }
} 