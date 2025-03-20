package com.recruitment.entity.DO;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Job {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long enterpriseId; // 对应 enterprise_users 表的 id (外键)
    private String title; // 岗位名称
    private String description; // 岗位描述
    private String requirements; // 岗位要求
    private String salaryRange; // 薪资范围
    private String location; // 工作地点
    private JobStatus status; // 使用枚举
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 枚举类型：岗位状态
    public enum JobStatus {
        OPEN, CLOSED, DRAFT
    }
}