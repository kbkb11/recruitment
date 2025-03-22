package com.recruitment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.recruitment.entity.DO.Job;
import org.apache.ibatis.annotations.*; // 导入 MyBatis 注解
import java.util.List;
import java.util.Optional;

@Mapper // 使用 MyBatis 的 @Mapper 注解
public interface JobMapper extends BaseMapper<Job> {

    @Select("SELECT * FROM job WHERE enterprise_id = #{enterpriseId}")
    List<Job> selectByEnterpriseId(Long enterpriseId);

    @Select("SELECT * FROM job WHERE status = #{status}")
    List<Job> selectByStatus(Job.JobStatus status);

    @Select("SELECT * FROM job WHERE enterprise_id = #{enterpriseId} AND status = #{status}")
    List<Job> selectByEnterpriseIdAndStatus(@Param("enterpriseId") Long enterpriseId, @Param("status") Job.JobStatus status);

    @Select("SELECT * FROM job")
    List<Job> selectAll();

    @Select("SELECT * FROM job WHERE title LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%') OR requirements LIKE CONCAT('%', #{keyword}, '%')")
    List<Job> searchByKeyword(String keyword);
}