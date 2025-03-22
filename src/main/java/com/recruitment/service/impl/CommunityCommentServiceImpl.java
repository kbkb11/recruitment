package com.recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.recruitment.entity.DO.CommunityComment;
import com.recruitment.mapper.CommunityCommentMapper;
import com.recruitment.service.CommunityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommunityCommentServiceImpl implements CommunityCommentService {

    @Autowired
    private CommunityCommentMapper commentMapper;

    @Override
    @Transactional
    public CommunityComment addComment(CommunityComment comment) {
        // 设置创建时间和更新时间
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        
        // 如果是回复其他评论
        if (comment.getParentId() != null) {
            // 获取父评论
            CommunityComment parentComment = commentMapper.selectById(comment.getParentId());
            if (parentComment == null) {
                throw new RuntimeException("父评论不存在");
            }
            
            // 设置层级：如果父评论是第二层或以上，保持在第二层
            comment.setLevel(Math.min(parentComment.getLevel() + 1, 2));
            
            // 设置根评论ID
            if (parentComment.getRootId() != null) {
                comment.setRootId(parentComment.getRootId());
            } else {
                comment.setRootId(parentComment.getId());
            }
        } else {
            // 直接评论帖子
            comment.setLevel(1);
            comment.setRootId(null);
        }
        
        commentMapper.insert(comment);
        return comment;
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        // 检查评论是否存在且属于该用户
        CommunityComment comment = commentMapper.selectById(commentId);
        if (comment == null || !comment.getUserId().equals(userId)) {
            throw new RuntimeException("评论不存在或无权限删除");
        }
        
        // 如果是根评论，删除所有子评论
        if (comment.getRootId() == null) {
            LambdaQueryWrapper<CommunityComment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CommunityComment::getRootId, commentId);
            commentMapper.delete(wrapper);
        }
        
        // 删除当前评论
        commentMapper.deleteById(commentId);
    }

    @Override
    public Page<CommunityComment> getPostComments(Long postId, Integer pageNum, Integer pageSize) {
        Page<CommunityComment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CommunityComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommunityComment::getPostId, postId)
               .isNull(CommunityComment::getParentId)  // 只获取直接评论
               .orderByDesc(CommunityComment::getCreatedAt);
        
        return commentMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<CommunityComment> getCommentReplies(Long commentId, Integer pageNum, Integer pageSize) {
        Page<CommunityComment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CommunityComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommunityComment::getParentId, commentId)
               .orderByAsc(CommunityComment::getCreatedAt);
        
        return commentMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<CommunityComment> getCommentTree(Long postId, Integer pageNum, Integer pageSize) {
        Page<CommunityComment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CommunityComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommunityComment::getPostId, postId)
               .orderByAsc(CommunityComment::getCreatedAt);
        
        return commentMapper.selectPage(page, wrapper);
    }
} 