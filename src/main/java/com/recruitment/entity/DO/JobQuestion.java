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
public class JobQuestion {

    private Long id;
    private Long userId;  // 对应 normal_users 表的 id (外键)
    private Long jobId;   // 对应 jobs 表的 id (外键)
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}