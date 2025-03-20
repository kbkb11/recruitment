package com.recruitment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.recruitment.entity.DO.EnterpriseUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EnterpriseUserMapper extends BaseMapper<EnterpriseUser> {

    @Select("SELECT * FROM enterprise_user WHERE username = #{username}")
    EnterpriseUser findByUsername(String username);
}