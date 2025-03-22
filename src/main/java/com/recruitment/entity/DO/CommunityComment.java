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
public class CommunityComment {
    public static final int MAX_COMMENT_LEVEL = 2;  // 最大评论层级为2

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long postId;      // 对应的帖子ID
    private Long userId;      // 评论用户ID
    private Long parentId;    // 父评论ID，用于实现盖楼功能，如果是直接评论帖子则为null
    private Long rootId;      // 根评论ID，用于标识属于同一个评论树的评论
    private String content;   // 评论内容
    private Integer level;    // 评论层级，用于控制盖楼深度
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
