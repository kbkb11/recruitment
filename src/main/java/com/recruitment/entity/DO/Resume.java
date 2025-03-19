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
public class Resume {

    private Long id;
    private Long userId; // 对应 normal_users 表的 id (外键)
    private String name;
    private Short age; // 使用 Short，因为 TINYINT UNSIGNED 在 Java 中没有直接对应的类型
    private Gender gender; // 使用枚举
    private String education;
    private String phoneNumber;
    private String skills;
    private String experience;
    private String projects;
    private String awards;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 枚举类型：性别
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}