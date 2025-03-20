package com.recruitment.service.strategy.impl;

import com.google.gson.Gson;
import com.recruitment.entity.DO.EnterpriseUser;
import com.recruitment.mapper.EnterpriseUserMapper;
import com.recruitment.service.strategy.UserStrategy;
import com.recruitment.utils.TokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EnterpriseUserStrategy implements UserStrategy {
    private static final Logger log = LogManager.getLogger(EnterpriseUserStrategy.class);

    private final EnterpriseUserMapper enterpriseUserMapper;
    private final RedisTemplate redisTemplate;

    public EnterpriseUserStrategy(EnterpriseUserMapper enterpriseUserMapper, RedisTemplate redisTemplate) {
        this.enterpriseUserMapper = enterpriseUserMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void register(String username, String password, String phoneNumber, Object additionalInfo) {
        if (additionalInfo instanceof Map) {
            Map<String, Object> info = (Map<String, Object>) additionalInfo;
            String companyName = (String) info.get("companyName");
            String industry = (String) info.get("industry");

            EnterpriseUser existingUser = enterpriseUserMapper.findByUsername(username);
            if (existingUser != null) {
                System.out.println("用户已存在: " + username);
                return;
            }

            EnterpriseUser enterpriseUser = new EnterpriseUser();
            enterpriseUser.setUsername(username);
            enterpriseUser.setPassword(password);
            enterpriseUser.setPhoneNumber(phoneNumber);
            enterpriseUser.setCompanyName(companyName);
            enterpriseUser.setIndustry(industry);

            enterpriseUserMapper.insert(enterpriseUser);
            System.out.println("企业用户注册成功: " + username);

        } else {
            log.error("注册信息不为map格式");
            throw new RuntimeException("注册信息不为map格式");
        }
    }

    @Override
    public String login(String username, String password) {
        EnterpriseUser existingUser = enterpriseUserMapper.findByUsername(username);
        if (existingUser != null) {
            System.out.println("用户已存在: " + username);
            return null;
        }

        String token = TokenUtil.generateToken();
        if (existingUser.getPassword().equals(password)) {
            String jsonString = new Gson().toJson(existingUser);
            redisTemplate.opsForValue().set(token, existingUser);
        } else {
            log.error("密码错误");
            return null;
        }

        return token;
    }
}