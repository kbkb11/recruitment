package com.recruitment.controller;

import com.recruitment.common.Result;
import com.recruitment.entity.DO.Admin;
import com.recruitment.entity.DO.NormalUser;
import com.recruitment.entity.DO.EnterpriseUser;
import com.recruitment.entity.DO.FriendshipLink;
import com.recruitment.service.AdminService;
import com.recruitment.service.FriendshipLinkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final FriendshipLinkService friendshipLinkService;

    public AdminController(AdminService adminService, FriendshipLinkService friendshipLinkService) {
        this.adminService = adminService;
        this.friendshipLinkService = friendshipLinkService;
    }

    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        return adminService.login(username, password);
    }

    @PutMapping("/update")
    public Result updateAdmin(@RequestBody Admin admin) {
        return adminService.updateAdmin(admin);
    }

    @GetMapping("/list")
    public Result listAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return Result.success(admins);
    }

    @GetMapping("/users/normal")
    public Result listNormalUsers() {
        List<NormalUser> users = adminService.getAllNormalUsers();
        return Result.success(users);
    }

    @GetMapping("/users/enterprise")
    public Result listEnterpriseUsers() {
        List<EnterpriseUser> users = adminService.getAllEnterpriseUsers();
        return Result.success(users);
    }

    @PutMapping("/users/normal/{userId}/status")
    public Result toggleNormalUserStatus(
            @PathVariable Long userId,
            @RequestParam boolean isEnabled) {
        return adminService.toggleNormalUserStatus(userId, isEnabled);
    }

    @PutMapping("/users/enterprise/{enterpriseId}/status")
    public Result toggleEnterpriseUserStatus(
            @PathVariable Long enterpriseId,
            @RequestParam boolean isEnabled) {
        return adminService.toggleEnterpriseUserStatus(enterpriseId, isEnabled);
    }

    @GetMapping("/users/normal/search")
    public Result searchNormalUsers(@RequestParam String keyword) {
        List<NormalUser> users = adminService.searchNormalUsers(keyword);
        return Result.success(users);
    }

    @GetMapping("/users/enterprise/search")
    public Result searchEnterpriseUsers(@RequestParam String keyword) {
        List<EnterpriseUser> users = adminService.searchEnterpriseUsers(keyword);
        return Result.success(users);
    }

    // 友情链接管理接口
    @PostMapping("/friendship-links")
    public Result addFriendshipLink(@RequestBody FriendshipLink friendshipLink) {
        try {
            FriendshipLink savedLink = friendshipLinkService.addFriendshipLink(friendshipLink);
            return Result.success(savedLink);
        } catch (Exception e) {
            return Result.failure("添加友情链接失败：" + e.getMessage());
        }
    }

    @PutMapping("/friendship-links/{id}")
    public Result updateFriendshipLink(@PathVariable Long id, @RequestBody FriendshipLink friendshipLink) {
        try {
            friendshipLink.setId(id);
            FriendshipLink updatedLink = friendshipLinkService.updateFriendshipLink(friendshipLink);
            return Result.success(updatedLink);
        } catch (Exception e) {
            return Result.failure("更新友情链接失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/friendship-links/{id}")
    public Result deleteFriendshipLink(@PathVariable Long id) {
        try {
            friendshipLinkService.deleteFriendshipLink(id);
            return Result.success("删除友情链接成功");
        } catch (Exception e) {
            return Result.failure("删除友情链接失败：" + e.getMessage());
        }
    }

    @GetMapping("/friendship-links")
    public Result getAllFriendshipLinks() {
        try {
            List<FriendshipLink> links = friendshipLinkService.getAllFriendshipLinks();
            return Result.success(links);
        } catch (Exception e) {
            return Result.failure("获取友情链接列表失败：" + e.getMessage());
        }
    }

    @GetMapping("/friendship-links/{id}")
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