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
@ToString
@Builder
public class RecruitmentAd {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long enterpriseId; // 对应 enterprise_users 表的 id (外键)
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
    private Integer status; // 0: 待审核, 1: 已通过, 2: 已拒绝
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
