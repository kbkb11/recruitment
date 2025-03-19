package com.recruitment.entity.DO;

import java.sql.Timestamp;

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
    private Long id;
    private Long enterpriseId; // 对应 enterprise_users 表的 id (外键)
    private String title;
    private String description;
    private String requirements;
    private String salaryRange;
    private String location;
    private JobStatus status; // 使用枚举
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 枚举类型：岗位状态
    public enum JobStatus {
        OPEN, CLOSED
    }
}