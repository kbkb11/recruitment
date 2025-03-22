package com.recruitment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.recruitment.entity.DO.EnterpriseUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface EnterpriseUserMapper extends BaseMapper<EnterpriseUser> {

    @Select("SELECT * FROM enterprise_user WHERE username = #{username}")
    EnterpriseUser findByUsername(String username);

    @Select("SELECT * FROM enterprise_user WHERE company_name LIKE CONCAT('%', #{keyword}, '%')")
    List<EnterpriseUser> searchByKeyword(String keyword);
}