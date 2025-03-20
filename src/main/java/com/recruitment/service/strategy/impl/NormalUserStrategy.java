package com.recruitment.service.strategy.impl;

import com.google.gson.Gson;
import com.recruitment.entity.DO.NormalUser;
import com.recruitment.mapper.NormalUserMapper; // 假设你创建了这个 Mapper
import com.recruitment.service.strategy.UserStrategy;
import com.recruitment.utils.TokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Map;

@Service
public class NormalUserStrategy implements UserStrategy {
    private static final Logger log = LogManager.getLogger(NormalUserStrategy.class);
    private final NormalUserMapper normalUserMapper;
    private final RedisTemplate redisTemplate;

    public NormalUserStrategy(NormalUserMapper normalUserMapper, RedisTemplate redisTemplate) {
        this.normalUserMapper = normalUserMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void register(String username, String password, String phoneNumber, Object additionalInfo) {
        if (additionalInfo instanceof Map) {
            Map<String, Object> info = (Map<String, Object>) additionalInfo;
            String genderStr = (String) info.get("gender");
            Date birthdate = (Date) info.get("birthdate");

            NormalUser user = normalUserMapper.findByUsername(username);
            if (user != null) {
                log.error("用户名已存在: {}", username);
                throw new RuntimeException("用户名已存在");
            }

            NormalUser normalUser = new NormalUser();
            normalUser.setUsername(username);
            normalUser.setPassword(password);
            normalUser.setPhoneNumber(phoneNumber);

            if (genderStr != null) {
                normalUser.setGender(NormalUser.Gender.valueOf(genderStr.toUpperCase()));
            }
            normalUser.setBirthdate(birthdate);

            normalUserMapper.insert(normalUser);
            log.info("普通用户注册成功: {}", username);

        } else {
            log.error("注册信息不为map格式");
            throw new RuntimeException("注册信息不为map格式");
        }
    }

    @Override
    public String login(String username, String password) {
        NormalUser normalUser = normalUserMapper.findByUsername(username);
        if (normalUser != null) {
            log.error("用户已存在: {}", username);
            return null;
        }

        String token = TokenUtil.generateToken();
        if (normalUser.getPassword().equals(password)) {
            String jsonString = new Gson().toJson(normalUser);
            redisTemplate.opsForValue().set(token, normalUser);
        } else {
            log.error("密码错误");
            return null;
        }

        return token;
    }
}