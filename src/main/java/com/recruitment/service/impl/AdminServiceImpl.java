package com.recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.recruitment.common.Result;
import com.recruitment.entity.DO.Admin;
import com.recruitment.entity.DO.NormalUser;
import com.recruitment.entity.DO.EnterpriseUser;
import com.recruitment.mapper.AdminMapper;
import com.recruitment.mapper.NormalUserMapper;
import com.recruitment.mapper.EnterpriseUserMapper;
import com.recruitment.service.AdminService;
import com.recruitment.utils.PasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;
    private final NormalUserMapper normalUserMapper;
    private final EnterpriseUserMapper enterpriseUserMapper;

    public AdminServiceImpl(AdminMapper adminMapper,
                            NormalUserMapper normalUserMapper,
                            EnterpriseUserMapper enterpriseUserMapper) {
        this.adminMapper = adminMapper;
        this.normalUserMapper = normalUserMapper;
        this.enterpriseUserMapper = enterpriseUserMapper;
    }

    @Override
    public Result login(String username, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Admin admin = adminMapper.selectOne(queryWrapper);

        if (admin == null) {
            return Result.failure("管理员不存在");
        }

        if (!PasswordUtils.verify(password, admin.getPassword())) {
            return Result.failure("密码错误");
        }

        return Result.success(admin);
    }

    @Override
    @Transactional
    public Result createAdmin(Admin admin) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", admin.getUsername());
        if (adminMapper.selectCount(queryWrapper) > 0) {
            return Result.failure("用户名已存在");
        }

        admin.setPassword(PasswordUtils.encrypt(admin.getPassword()));
        admin.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        admin.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        adminMapper.insert(admin);
        return Result.success(admin);
    }

    @Override
    @Transactional
    public Result updateAdmin(Admin admin) {
        Admin existingAdmin = adminMapper.selectById(admin.getId());
        if (existingAdmin == null) {
            return Result.failure("管理员不存在");
        }

        if (!admin.getPassword().equals(existingAdmin.getPassword())) {
            admin.setPassword(PasswordUtils.encrypt(admin.getPassword()));
        }

        admin.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        adminMapper.updateById(admin);
        return Result.success(admin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminMapper.selectList(null);
    }

    @Override
    public List<NormalUser> getAllNormalUsers() {
        return normalUserMapper.selectList(null);
    }

    @Override
    public List<EnterpriseUser> getAllEnterpriseUsers() {
        return enterpriseUserMapper.selectList(null);
    }

    @Override
    @Transactional
    public Result toggleNormalUserStatus(Long userId, boolean isEnabled) {
        NormalUser user = normalUserMapper.selectById(userId);
        if (user == null) {
            return Result.failure("用户不存在");
        }
        
        user.setEnabled(isEnabled);
        user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        normalUserMapper.updateById(user);
        return Result.success("状态更新成功");
    }

    @Override
    @Transactional
    public Result toggleEnterpriseUserStatus(Long enterpriseId, boolean isEnabled) {
        EnterpriseUser user = enterpriseUserMapper.selectById(enterpriseId);
        if (user == null) {
            return Result.failure("企业用户不存在");
        }
        
        user.setEnabled(isEnabled);
        user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        enterpriseUserMapper.updateById(user);
        return Result.success("状态更新成功");
    }

    @Override
    public List<NormalUser> searchNormalUsers(String keyword) {
        QueryWrapper<NormalUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", keyword)
                   .or()
                   .like("phone_number", keyword);
        return normalUserMapper.selectList(queryWrapper);
    }

    @Override
    public List<EnterpriseUser> searchEnterpriseUsers(String keyword) {
        return enterpriseUserMapper.searchByKeyword(keyword);
    }
} 