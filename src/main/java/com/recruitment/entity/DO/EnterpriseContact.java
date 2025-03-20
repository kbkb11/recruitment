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
public class EnterpriseContact {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long enterpriseId; // 对应 enterprise_users 表的 id (外键, 且唯一)
    private String contactName;
    private String phoneNumber;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
