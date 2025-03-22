package com.recruitment.controller;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.FriendshipLink;
import com.recruitment.service.FriendshipLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendship-links")
public class FriendshipLinkController {

    @Autowired
    private FriendshipLinkService friendshipLinkService;

    @PostMapping
    public Result addFriendshipLink(@RequestBody FriendshipLink friendshipLink) {
        try {
            FriendshipLink savedLink = friendshipLinkService.addFriendshipLink(friendshipLink);
            return Result.success(savedLink);
        } catch (Exception e) {
            return Result.failure("添加友情链接失败：" + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result updateFriendshipLink(@PathVariable Long id, @RequestBody FriendshipLink friendshipLink) {
        try {
            friendshipLink.setId(id);
            FriendshipLink updatedLink = friendshipLinkService.updateFriendshipLink(friendshipLink);
            return Result.success(updatedLink);
        } catch (Exception e) {
            return Result.failure("更新友情链接失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteFriendshipLink(@PathVariable Long id) {
        try {
            friendshipLinkService.deleteFriendshipLink(id);
            return Result.success("删除友情链接成功");
        } catch (Exception e) {
            return Result.failure("删除友情链接失败：" + e.getMessage());
        }
    }

    @GetMapping
    public Result getAllFriendshipLinks() {
        try {
            List<FriendshipLink> links = friendshipLinkService.getAllFriendshipLinks();
            return Result.success(links);
        } catch (Exception e) {
            return Result.failure("获取友情链接列表失败：" + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getFriendshipLinkById(@PathVariable Long id) {
        try {
            FriendshipLink link = friendshipLinkService.getFriendshipLinkById(id);
            if (link != null) {
                return Result.success(link);
            } else {
                return Result.failure("友情链接不存在");
            }
        } catch (Exception e) {
            return Result.failure("获取友情链接失败：" + e.getMessage());
        }
    }
} 