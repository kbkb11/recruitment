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
@ToString(exclude = "password")
@Builder
public class Admin {
    private Long id;
    private String username;
    private String password; // 强烈建议：实际应用中，不要直接存储明文密码！至少要加盐哈希！
    private String phoneNumber;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}