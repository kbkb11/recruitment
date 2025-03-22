package com.recruitment.service.impl;

import com.recruitment.entity.DO.FriendshipLink;
import com.recruitment.mapper.FriendshipLinkMapper;
import com.recruitment.service.FriendshipLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendshipLinkServiceImpl implements FriendshipLinkService {

    @Autowired
    private FriendshipLinkMapper friendshipLinkMapper;

    @Override
    @Transactional
    public FriendshipLink addFriendshipLink(FriendshipLink friendshipLink) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        friendshipLink.setCreatedAt(now);
        friendshipLink.setUpdatedAt(now);
        friendshipLinkMapper.insert(friendshipLink);
        return friendshipLink;
    }

    @Override
    @Transactional
    public FriendshipLink updateFriendshipLink(FriendshipLink friendshipLink) {
        friendshipLink.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        friendshipLinkMapper.updateById(friendshipLink);
        return friendshipLink;
    }

    @Override
    @Transactional
    public void deleteFriendshipLink(Long id) {
        friendshipLinkMapper.deleteById(id);
    }

    @Override
    public List<FriendshipLink> getAllFriendshipLinks() {
        return friendshipLinkMapper.selectList(null);
    }

    @Override
    public FriendshipLink getFriendshipLinkById(Long id) {
        return friendshipLinkMapper.selectById(id);
    }
} 