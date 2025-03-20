package com.recruitment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.recruitment.entity.DO.NormalUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NormalUserMapper extends BaseMapper<NormalUser>{
    @Select("SELECT * FROM normal_user WHERE username = #{username}")
    NormalUser findByUsername(String username);
}