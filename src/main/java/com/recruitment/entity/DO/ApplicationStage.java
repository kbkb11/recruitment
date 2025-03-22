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
public class ApplicationStage {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long applicationId; // 对应 applications 表的 id (外键)
    private StageType stageName; // 阶段类型
    private StageStatus status; // 阶段状态
    private Timestamp endTime; // 结束时间
    private String remarks; // 备注信息

    // 阶段类型枚举
    public enum StageType {
        INITIAL,       // 初步筛选
        INTERVIEW,     // 面试阶段
        OFFER,         // 发放Offer阶段
    }

    // 枚举类型：申请阶段类型
    public enum StageStatus {
        PENDING,  // 待处理
        PASSED,   // 通过
        FAILED    // 未通过
    }
}
