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
public class EnterpriseContact {
    private Long id;
    private Long enterpriseId; // 对应 enterprise_users 表的 id (外键, 且唯一)
    private String address;
    private String zipCode;
    private String email;
    private String phoneNumber;
    private String enterpriseSize;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
