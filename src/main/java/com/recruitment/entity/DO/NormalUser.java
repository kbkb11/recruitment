package com.recruitment.entity.DO;

import java.sql.Timestamp;
import java.util.Date;

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

    private Long id;
    private String username;
    private String password;
    private String phoneNumber;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Gender gender;
    private Date birthdate;

    // 枚举类型 Gender
    public enum Gender {
        MALE, FEMALE
    }
}