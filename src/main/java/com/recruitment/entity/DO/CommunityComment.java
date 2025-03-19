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
public class CommunityComment {

    private Long id;
    private Long postId;     // 对应 community_posts 表的 id (外键)
    private Long userId;     // 对应 normal_users 表的 id (外键)
    private String content;
    private Long parentCommentId; // 父评论 ID (可以为 null)
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
