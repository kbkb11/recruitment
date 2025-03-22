package com.recruitment.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.recruitment.entity.DO.CommunityComment;
import com.recruitment.service.CommunityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community/comments")
public class CommunityCommentController {

    @Autowired
    private CommunityCommentService commentService;

    @PostMapping
    public CommunityComment addComment(@RequestBody CommunityComment comment) {
        return commentService.addComment(comment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
    }

    @GetMapping("/post/{postId}")
    public Page<CommunityComment> getPostComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return commentService.getPostComments(postId, pageNum, pageSize);
    }

    @GetMapping("/{commentId}/replies")
    public Page<CommunityComment> getCommentReplies(
            @PathVariable Long commentId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return commentService.getCommentReplies(commentId, pageNum, pageSize);
    }

    @GetMapping("/post/{postId}/tree")
    public Page<CommunityComment> getCommentTree(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return commentService.getCommentTree(postId, pageNum, pageSize);
    }
} 