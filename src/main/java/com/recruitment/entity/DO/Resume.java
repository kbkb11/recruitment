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
public class Resume {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId; // 对应 normal_users 表的 id (外键)
    private String name;
    private Short age; // 使用 Short，因为 TINYINT UNSIGNED 在 Java 中没有直接对应的类型
    private Gender gender; // 使用枚举
    private String education; // 学历
    private String phoneNumber; // 手机号
    private String skills; // 技能
    private String experience; // 工作经验
    private String projects; // 项目经验
    private String awards; // 获奖情况
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 枚举类型：性别
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}