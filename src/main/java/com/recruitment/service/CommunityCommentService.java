package com.recruitment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.recruitment.entity.DO.CommunityComment;

public interface CommunityCommentService {
    // 发表评论
    CommunityComment addComment(CommunityComment comment);
    
    // 删除评论
    void deleteComment(Long commentId, Long userId);
    
    // 获取帖子的评论列表（分页）
    Page<CommunityComment> getPostComments(Long postId, Integer pageNum, Integer pageSize);
    
    // 获取评论的回复列表（分页）
    Page<CommunityComment> getCommentReplies(Long commentId, Integer pageNum, Integer pageSize);
    
    // 获取评论树（包含所有回复）
    Page<CommunityComment> getCommentTree(Long postId, Integer pageNum, Integer pageSize);
} 