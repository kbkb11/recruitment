package com.recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.recruitment.entity.DO.CommunityComment;
import com.recruitment.mapper.CommunityCommentMapper;
import com.recruitment.service.CommunityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class CommunityCommentServiceImpl2 implements CommunityCommentService {

    @Autowired
    private CommunityCommentMapper commentMapper;

    private static final int MAX_LEVEL = 2;

    @Override
    @Transactional
    public CommunityComment addComment(CommunityComment comment) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);
        
        // 如果是回复其他评论
        if (comment.getParentId() != null) {
            // 获取父评论
            CommunityComment parentComment = commentMapper.selectById(comment.getParentId());
            if (parentComment == null) {
                throw new RuntimeException("父评论不存在");
            }
            
            // 如果父评论是第一层评论，设置为第二层
            if (parentComment.getLevel() == 1) {
                comment.setLevel(2);
                comment.setRootId(parentComment.getId());
            } 
            // 如果父评论是第二层评论，保持在第二层，但更新父评论引用
            else if (parentComment.getLevel() == 2) {
                comment.setLevel(2);
                comment.setRootId(parentComment.getRootId());
            }
            // 不允许回复超过二层的评论
            else {
                throw new RuntimeException("评论层级已达到上限");
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
        
        // 如果是第一层评论，删除所有回复
        if (comment.getLevel() == 1) {
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
               .eq(CommunityComment::getLevel, 1)  // 只获取第一层评论
               .orderByDesc(CommunityComment::getCreatedAt);
        
        return commentMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<CommunityComment> getCommentReplies(Long commentId, Integer pageNum, Integer pageSize) {
        // 获取评论信息
        CommunityComment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }

        Page<CommunityComment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CommunityComment> wrapper = new LambdaQueryWrapper<>();
        
        // 如果是第一层评论，获取其所有回复
        if (comment.getLevel() == 1) {
            wrapper.eq(CommunityComment::getRootId, commentId);
        }
        // 如果是第二层评论，获取同一个根评论下的所有第二层评论
        else if (comment.getLevel() == 2) {
            wrapper.eq(CommunityComment::getRootId, comment.getRootId())
                   .eq(CommunityComment::getLevel, 2);
        }
        
        wrapper.orderByAsc(CommunityComment::getCreatedAt);
        return commentMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<CommunityComment> getCommentTree(Long postId, Integer pageNum, Integer pageSize) {
        // 先获取所有一级评论
        Page<CommunityComment> firstLevelPage = getPostComments(postId, pageNum, pageSize);
        
        // 对于每个一级评论，获取其二级评论
        for (CommunityComment comment : firstLevelPage.getRecords()) {
            Page<CommunityComment> replies = getCommentReplies(comment.getId(), 1, 50);
            // 这里可以将回复添加到一级评论的某个字段中，需要在实体类中添加相应字段
            // comment.setReplies(replies.getRecords());
        }
        
        return firstLevelPage;
    }
} 