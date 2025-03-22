CREATE TABLE community_comments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL COMMENT '对应的帖子ID',
    user_id BIGINT NOT NULL COMMENT '评论用户ID',
    parent_id BIGINT COMMENT '父评论ID，用于实现盖楼功能',
    root_id BIGINT COMMENT '根评论ID，用于标识属于同一个评论树的评论',
    content TEXT NOT NULL COMMENT '评论内容',
    level INT NOT NULL DEFAULT 1 COMMENT '评论层级，用于控制盖楼深度',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES community_posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES normal_users(id) ON DELETE CASCADE,
    INDEX idx_post_id (post_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_root_id (root_id)
); 