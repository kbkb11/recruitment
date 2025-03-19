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
public class CommunityPost {

    private Long id;
    private Long userId; // 对应 normal_users 表的 id (外键)
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
