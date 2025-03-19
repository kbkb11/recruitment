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
public class HomepageInfo {

    private Long id;
    private String title;
    private String content;
    private String type;
    private Boolean isActive; // 使用 Boolean 包装类，因为 SQL 中的 BOOLEAN 允许 NULL 值
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
