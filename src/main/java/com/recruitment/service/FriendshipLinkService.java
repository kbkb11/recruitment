package com.recruitment.service;

import com.recruitment.entity.DO.FriendshipLink;
import java.util.List;

public interface FriendshipLinkService {
    // 添加友情链接
    FriendshipLink addFriendshipLink(FriendshipLink friendshipLink);
    
    // 更新友情链接
    FriendshipLink updateFriendshipLink(FriendshipLink friendshipLink);
    
    // 删除友情链接
    void deleteFriendshipLink(Long id);
    
    // 获取所有友情链接
    List<FriendshipLink> getAllFriendshipLinks();
    
    // 根据ID获取友情链接
    FriendshipLink getFriendshipLinkById(Long id);
} 