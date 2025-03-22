package com.recruitment.service;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.Admin;
import com.recruitment.entity.DO.NormalUser;
import com.recruitment.entity.DO.EnterpriseUser;
import java.util.List;

public interface AdminService {
    // 管理员登录
    Result login(String username, String password);
    
    // 创建新管理员
    Result createAdmin(Admin admin);
    
    // 更新管理员信息
    Result updateAdmin(Admin admin);
    
    // 获取所有管理员列表
    List<Admin> getAllAdmins();
    
    // 获取所有普通用户
    List<NormalUser> getAllNormalUsers();
    
    // 获取所有企业用户
    List<EnterpriseUser> getAllEnterpriseUsers();
    
    // 禁用/启用普通用户
    Result toggleNormalUserStatus(Long userId, boolean isEnabled);
    
    // 禁用/启用企业用户
    Result toggleEnterpriseUserStatus(Long enterpriseId, boolean isEnabled);
    
    // 根据关键字搜索用户
    List<NormalUser> searchNormalUsers(String keyword);
    
    // 根据关键字搜索企业用户
    List<EnterpriseUser> searchEnterpriseUsers(String keyword);
} 