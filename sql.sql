-- 普通用户表：存储普通用户的通用信息和扩展信息
CREATE TABLE `normal_user` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 通用字段
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL UNIQUE,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 普通用户专有字段
  `gender` ENUM('male', 'female'),
  `birthdate` DATE
);

-- 企业用户表：存储企业用户的通用信息和扩展信息
CREATE TABLE `enterprise_user` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 通用字段
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL UNIQUE,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 企业用户专有字段
  `company_name` VARCHAR(100) NOT NULL,
  `industry` VARCHAR(50)
);

-- 管理员表：存储管理员的通用信息（管理员一般无额外扩展字段）
CREATE TABLE `admin` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 通用字段
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL UNIQUE,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 岗位信息表：存储招聘岗位的信息，通过 enterprise_id 关联到 enterprise_user 表
CREATE TABLE `job` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 岗位ID
  `enterprise_id` INT UNSIGNED NOT NULL,           -- 发布岗位的企业用户ID
  `title` VARCHAR(200) NOT NULL,                   -- 岗位标题
  `description` VARCHAR(255) NOT NULL,             -- 岗位描述
  `requirements` VARCHAR(255) NOT NULL,            -- 任职要求
  `salary_range` VARCHAR(40),                      -- 薪资范围
  `location` VARCHAR(20),                          -- 工作地点
  `status` ENUM('open', 'closed', 'draft') NOT NULL DEFAULT 'draft',  -- 岗位状态
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,           -- 创建时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
  FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise_user`(`id`) ON DELETE CASCADE
);

-- 简历表：存储用户的简历信息，通过 user_id 关联到 normal_user 表
CREATE TABLE `resume` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 简历ID
  `user_id` INT UNSIGNED NOT NULL,                -- 拥有该简历的普通用户ID
  `name` VARCHAR(20) NOT NULL,                    -- 姓名
  `age` TINYINT UNSIGNED,                         -- 年龄
  `gender` ENUM('Male', 'Female', 'Other'),       -- 性别
  `education` VARCHAR(255),                       -- 教育经历
  `phone_number` VARCHAR(20),                     -- 电话号码
  `skills` VARCHAR(255),                          -- 个人技能
  `experience` VARCHAR(255),                      -- 工作经历
  `projects` VARCHAR(255),                        -- 项目经历
  `awards` VARCHAR(255),                          -- 获奖情况
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- 创建时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
  FOREIGN KEY (`user_id`) REFERENCES `normal_user`(`id`) ON DELETE CASCADE
);

-- 申请表：存储用户的岗位申请记录，关联到 normal_user 表的普通用户、job 表的岗位信息和 resume 表的简历
CREATE TABLE `application` (
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- 申请ID
    `user_id` BIGINT UNSIGNED NOT NULL,                      -- 关联普通用户ID
    `job_id` BIGINT UNSIGNED NOT NULL,                       -- 关联岗位ID
    `resume_id` BIGINT UNSIGNED NOT NULL,                    -- 关联简历ID
    `apply_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,        -- 申请日期
    `status` ENUM('PENDING', 'REVIEWED', 'INTERVIEW', 'REJECTED', 'OFFERED', 'ACCEPTED', 'DECLINED')
        NOT NULL DEFAULT 'PENDING',                          -- 申请状态
    FOREIGN KEY (`user_id`) REFERENCES `normal_user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`job_id`) REFERENCES `job`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`resume_id`) REFERENCES `resume`(`id`) ON DELETE CASCADE
);


-- 申请阶段记录表：存储每个岗位申请在招聘流程中的不同阶段记录
CREATE TABLE `application_stage` (
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,      -- 阶段记录ID
    `application_id` BIGINT UNSIGNED NOT NULL,              -- 关联申请ID
    `stage_name` VARCHAR(50) NOT NULL,                      -- 阶段名称
    `status` ENUM('pending', 'passed', 'failed') NOT NULL DEFAULT 'pending', -- 阶段状态
    `remarks` VARCHAR(255),                                 -- 备注信息
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,       -- 创建时间
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    FOREIGN KEY (`application_id`) REFERENCES `application`(`id`) ON DELETE CASCADE
);

-- 友情链接表：存储友情网站的链接信息
CREATE TABLE `friendship_link` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 链接ID
  `link_name` VARCHAR(100) NOT NULL,              -- 链接名称
  `link_url` VARCHAR(255) NOT NULL,               -- 链接URL
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,         -- 创建时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 更新时间
);

-- 招聘沟通记录：存储用户关于岗位的提问记录
CREATE TABLE `job_question` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 记录ID
  `user_id` INT UNSIGNED NOT NULL,                -- 提问用户ID（普通用户）
  `job_id` INT UNSIGNED NOT NULL,                 -- 岗位ID
  `content` VARCHAR(255) NOT NULL,                -- 问题内容
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,         -- 提问时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
  FOREIGN KEY (`user_id`) REFERENCES `normal_user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`job_id`) REFERENCES `job`(`id`) ON DELETE CASCADE
);

-- 招聘广告表：存储企业发布的招聘广告信息，通过 enterprise_id 关联到 enterprise_user 表
CREATE TABLE `recruitment_ad` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 广告ID
  `enterprise_id` INT UNSIGNED NOT NULL,          -- 发布广告的企业用户ID
  `title` VARCHAR(200) NOT NULL,                  -- 广告标题
  `content` TEXT NOT NULL,                        -- 广告内容
  `start_date` DATE,                              -- 广告开始日期
  `end_date` DATE,                                -- 广告结束日期
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,         -- 创建时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
  FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise_user`(`id`) ON DELETE CASCADE
);

-- 企业联系信息表：存储企业的联系方式，通过 enterprise_id 关联到 enterprise_user 表（一个企业只能有一条联系信息）
CREATE TABLE `enterprise_contact` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 联系信息ID
  `enterprise_id` INT UNSIGNED NOT NULL UNIQUE,   -- 关联的企业用户ID
  `address` VARCHAR(255),                         -- 公司地址
  `zip_code` VARCHAR(20),                         -- 邮政编码
  `email` VARCHAR(100),                           -- 联系邮箱
  `phone_number` VARCHAR(20),                     -- 联系电话
  `enterprise_size` VARCHAR(20),                  -- 企业规模
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,         -- 创建时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
  FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise_user`(`id`) ON DELETE CASCADE
);

-- 联系人员表：存储企业相关的联系人信息，通过 enterprise_id 关联到 enterprise_user 表
CREATE TABLE `contact_person` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 联系人ID
  `enterprise_id` INT UNSIGNED NOT NULL,          -- 关联的企业用户ID
  `name` VARCHAR(100) NOT NULL,                   -- 联系人姓名
  `department` VARCHAR(100),                      -- 部门
  `position` VARCHAR(100),                        -- 职位
  `phone_number` VARCHAR(20),                     -- 联系电话
  `email` VARCHAR(100),                           -- 联系邮箱
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,         -- 创建时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
  FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise_user`(`id`) ON DELETE CASCADE
);

-- 首页信息表：存储系统首页展示的信息
CREATE TABLE `homepage_info` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 信息ID
  `title` VARCHAR(200) NOT NULL,                  -- 信息标题
  `content` VARCHAR(200) NOT NULL,                -- 信息内容
  `type` VARCHAR(50) NOT NULL,                    -- 信息类型
  `is_active` BOOLEAN NOT NULL DEFAULT TRUE,      -- 是否显示
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,         -- 创建时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 更新时间
);

-- 社区交流表：存储用户在社区发布的帖子，通过 user_id 关联到 normal_user 表
CREATE TABLE `community_post` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 帖子ID
  `user_id` INT UNSIGNED NOT NULL,                -- 发布帖子的用户ID
  `content` TEXT NOT NULL,                        -- 帖子内容
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,         -- 发布时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
  FOREIGN KEY (`user_id`) REFERENCES `normal_user`(`id`) ON DELETE CASCADE
);

-- 社区评论表：存储用户对帖子进行评论（或评论回复）
CREATE TABLE `community_comment` (
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 评论ID
  `post_id` INT UNSIGNED NOT NULL,                -- 评论所属的帖子ID
  `user_id` INT UNSIGNED NOT NULL,                -- 发表评论的用户ID
  `content` TEXT NOT NULL,                        -- 评论内容
  `parent_comment_id` INT UNSIGNED DEFAULT NULL,  -- 父评论ID（回复时使用）
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,         -- 评论时间
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
  FOREIGN KEY (`post_id`) REFERENCES `community_post`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `normal_user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`parent_comment_id`) REFERENCES `community_comment`(`id`) ON DELETE SET NULL
);
