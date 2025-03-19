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
@ToString
@Builder
public class RecruitmentAd {

    private Long id;
    private Long enterpriseId; // 对应 enterprise_users 表的 id (外键)
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
