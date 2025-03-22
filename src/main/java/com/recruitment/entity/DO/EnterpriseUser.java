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
@ToString(exclude = "password")
@Builder
public class EnterpriseUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String phoneNumber;
    private boolean enabled;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String companyName;
    private String industry;
}