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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;
    private final NormalUserMapper normalUserMapper;
    private final EnterpriseUserMapper enterpriseUserMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminMapper adminMapper,
                          NormalUserMapper normalUserMapper,
                          EnterpriseUserMapper enterpriseUserMapper,
                          PasswordEncoder passwordEncoder) {
        this.adminMapper = adminMapper;
        this.normalUserMapper = normalUserMapper;
        this.enterpriseUserMapper = enterpriseUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Result login(String username, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Admin admin = adminMapper.selectOne(queryWrapper);
        
        if (admin == null) {
            return Result.failure("管理员不存在");
        }
        
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            return Result.failure("密码错误");
        }
        
        return Result.success(admin);
    }

    @Override
    @Transactional
    public Result createAdmin(Admin admin) {
        // 检查用户名是否已存在
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", admin.getUsername());
        if (adminMapper.selectCount(queryWrapper) > 0) {
            return Result.failure("用户名已存在");
        }
        
        // 加密密码
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        admin.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        
        adminMapper.insert(admin);
        return Result.success(admin);
    }

    @Override
    @Transactional
    public Result updateAdmin(Admin admin) {
        // 获取当前登录的管理员
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        // 只允许管理员修改自己的信息
        Admin existingAdmin = adminMapper.selectById(admin.getId());
        if (existingAdmin == null) {
            return Result.failure("管理员不存在");
        }
        
        if (!existingAdmin.getUsername().equals(currentUsername)) {
            return Result.failure("无权修改其他管理员的信息");
        }
        
        // 如果密码被修改，需要重新加密
        if (!admin.getPassword().equals(existingAdmin.getPassword())) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        
        admin.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        adminMapper.updateById(admin);
        return Result.success(admin);
    }

    @Override
    @Transactional
    public Result deleteAdmin(Long id) {
        // 获取当前登录的管理员
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        // 检查要删除的管理员是否存在
        Admin adminToDelete = adminMapper.selectById(id);
        if (adminToDelete == null) {
            return Result.failure("管理员不存在");
        }
        
        // 不允许删除其他管理员
        if (!adminToDelete.getUsername().equals(currentUsername)) {
            return Result.failure("无权删除其他管理员");
        }
        
        if (adminMapper.deleteById(id) > 0) {
            return Result.success("删除成功");
        }
        return Result.failure("删除失败");
    }

    @Override
    public List<Admin> getAllAdmins() {
        // 获取当前登录的管理员
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        // 只返回当前管理员自己的信息
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", currentUsername);
        return adminMapper.selectList(queryWrapper);
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