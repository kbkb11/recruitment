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
public class Application {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;    // 对应 normal_users 表的 id (外键)
    private Long jobId;     // 对应 jobs 表的 id (外键)
    private Long resumeId;  // 对应 resumes 表的 id (外键)
    private Timestamp applyDate; // 申请时间
    private ApplicationStatus status; // 状态

    // 枚举类型：简历投递状态
    public enum ApplicationStatus {
        PENDING,    // 待处理
        REVIEWED,   // 已查看
        INTERVIEW,  // 邀请面试
        REJECTED,   // 已拒绝
        OFFERED,    // 发放offer
        ACCEPTED,   // 接受offer
        DECLINED    // 拒绝offer
    }
}