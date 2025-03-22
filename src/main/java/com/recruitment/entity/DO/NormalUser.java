package com.recruitment.entity.DO;

import java.sql.Timestamp;
import java.util.Date;

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
@ToString(exclude = "password")
@Builder
public class NormalUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String phoneNumber;
    private boolean enabled;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Gender gender;
    private Date birthdate;

    // 枚举类型 Gender
    public enum Gender {
        MALE, FEMALE
    }
}