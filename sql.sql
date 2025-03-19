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

-- 岗位信息表：存储招聘岗位的信息，通过 enterprise_id 关联到 users 表的企业用户
CREATE TABLE `job` (
  -- 岗位ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 发布岗位的企业用户ID，不能为空，关联到 users 表的 id 字段
  `enterprise_id` INT UNSIGNED NOT NULL,
  -- 岗位标题，不能为空
  `title` VARCHAR(200) NOT NULL,
  -- 岗位描述，不能为空
  `description` VARCHAR(255) NOT NULL,
  -- 任职要求，不能为空
  `requirements` VARCHAR(255) NOT NULL,
  -- 薪资范围，可以为空
  `salary_range` VARCHAR(40),
  -- 工作地点，可以为空
  `location` VARCHAR(20),
  -- 岗位状态：枚举类型，可选值为 'open' (开放), 'closed' (关闭)，默认为 'open'
  `status` ENUM('open', 'closed') NOT NULL DEFAULT 'open',
  -- 创建时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 外键约束：关联 users 表的 id 字段，ON DELETE CASCADE 表示当 users 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`enterprise_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- 简历表：存储用户的简历信息，通过 user_id 关联到 users 表的普通用户
CREATE TABLE `resume` (
  -- 简历ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 拥有该简历的用户ID，不能为空，关联到 users 表的 id 字段
  `user_id` INT UNSIGNED NOT NULL,
  -- 姓名
  `name` VARCHAR(20) NOT NULL,
  -- 年龄
  `age` TINYINT UNSIGNED,
  -- 性别，可以使用 ENUM 类型限制取值
  `gender` ENUM('Male', 'Female', 'Other'),
  -- 教育经历
  `education` VARCHAR(255),
  -- 电话号码
  `phone_number` VARCHAR(20),
  -- 个人技能
  `skills` VARCHAR(255),
  -- 工作经历
  `experience` VARCHAR(255),
  -- 项目经历
  `projects` VARCHAR(255),
  -- 获奖情况
  `awards` VARCHAR(255),
  -- 创建时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 外键约束：关联 users 表的 id 字段，ON DELETE CASCADE 表示当 users 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- 申请表：存储用户的岗位申请记录，关联到 users 表的普通用户、jobs 表的岗位信息和 resumes 表的简历
CREATE TABLE `application` (
  -- 申请ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 申请用户ID，不能为空，关联到 users 表的 id 字段
  `user_id` INT UNSIGNED NOT NULL,
  -- 申请的岗位ID，不能为空，关联到 jobs 表的 id 字段
  `job_id` INT UNSIGNED NOT NULL,
  -- 申请时使用的简历ID，不能为空，关联到 resumes 表的 id 字段
  `resume_id` INT UNSIGNED NOT NULL,
  -- 申请日期，默认为当前时间
  `apply_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 申请状态，可以为空
  `status` VARCHAR(50),
  -- 外键约束：关联 users 表的 id 字段，ON DELETE CASCADE 表示当 users 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  -- 外键约束：关联 jobs 表的 id 字段，ON DELETE CASCADE 表示当 jobs 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`job_id`) REFERENCES `jobs`(`id`) ON DELETE CASCADE,
  -- 外键约束：关联 resumes 表的 id 字段，ON DELETE CASCADE 表示当 resumes 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`resume_id`) REFERENCES `resumes`(`id`) ON DELETE CASCADE
);

-- 友情链接表：存储友情网站的链接信息
CREATE TABLE `friendship_link` (
  -- 链接ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 链接名称，不能为空
  `link_name` VARCHAR(100) NOT NULL,
  -- 链接URL，不能为空
  `link_url` VARCHAR(255) NOT NULL,
  -- 创建时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 招聘沟通记录
CREATE TABLE `job_question` (
  -- ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 提问用户ID，不能为空，关联到 users 表的 id 字段
  `user_id` INT UNSIGNED NOT NULL,
  -- 管理的岗位id
  `job_id` INT UNSIGNED NOT NULL,
  -- 内容
  `content` VARCHAR(255) NOT NULL,
  -- 提问时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 外键约束：关联 users 表的 id 字段，ON DELETE CASCADE 表示当 users 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
  FOREIGN KEY (`job_id`) REFERENCES `jobs`(`id`) ON DELETE CASCADE
);

-- 招聘广告表：存储企业发布的招聘广告信息，通过 enterprise_id 关联到 users 表的企业用户
CREATE TABLE `recruitment_ad` (
  -- 广告ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 发布广告的企业用户ID，不能为空，关联到 users 表的 id 字段
  `enterprise_id` INT UNSIGNED NOT NULL,
  -- 广告标题，不能为空
  `title` VARCHAR(200) NOT NULL,
  -- 广告内容，不能为空
  `content` TEXT NOT NULL,
  -- 广告开始日期，可以为空
  `start_date` DATE,
  -- 广告结束日期，可以为空
  `end_date` DATE,
  -- 创建时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 外键约束：关联 users 表的 id 字段，ON DELETE CASCADE 表示当 users 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`enterprise_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- 企业联系信息表：存储企业的联系方式，通过 enterprise_id 关联到 users 表的企业用户，一个企业只能有一条联系信息
CREATE TABLE `enterprise_contact` (
  -- 联系信息ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 关联的企业用户ID，不能为空，关联到 users 表的 id 字段，并且在整个表中唯一，保证一个企业只有一条联系信息
  `enterprise_id` INT UNSIGNED NOT NULL UNIQUE,
  -- 公司地址，可以为空
  `address` VARCHAR(255),
  -- 邮政编码，可以为空
  `zip_code` VARCHAR(20),
  -- 联系邮箱，可以为空
  `email` VARCHAR(100),
  -- 联系电话，可以为空
  `phone_number` VARCHAR(20),
  -- 企业规模，可以为空
  `enterprise_size` VARCHAR(20),
  -- 创建时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 外键约束：关联 users 表的 id 字段，ON DELETE CASCADE 表示当 users 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`enterprise_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- 联系人员表：存储企业相关的联系人信息，通过 enterprise_id 关联到 users 表的企业用户
CREATE TABLE `contact_person` (
  -- 联系人ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 关联的企业用户ID，不能为空，关联到 users 表的 id 字段
  `enterprise_id` INT UNSIGNED NOT NULL,
  -- 联系人姓名，不能为空
  `name` VARCHAR(100) NOT NULL,
  -- 部门，可以为空
  `department` VARCHAR(100),
  -- 职位，可以为空
  `position` VARCHAR(100),
  -- 联系电话，可以为空
  `phone_number` VARCHAR(20),
  -- 联系邮箱，可以为空
  `email` VARCHAR(100),
  -- 创建时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 外键约束：关联 users 表的 id 字段，ON DELETE CASCADE 表示当 users 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`enterprise_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- 首页信息表：存储系统首页展示的信息
CREATE TABLE `homepage_info` (
  -- 信息ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 信息标题，不能为空
  `title` VARCHAR(200) NOT NULL,
  -- 信息内容，不能为空
  `content` VARCHAR(200) NOT NULL,
  -- 信息类型，不能为空
  `type` VARCHAR(50) NOT NULL,
  -- 是否显示：布尔类型，默认为 TRUE (显示)
  `is_active` BOOLEAN NOT NULL DEFAULT TRUE,
  -- 创建时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 社区交流表：存储用户在社区发布的帖子，通过 user_id 关联到 users 表的普通用户
CREATE TABLE `community_post` (
  -- 帖子ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 发布帖子的用户ID，不能为空，关联到 users 表的 id 字段
  `user_id` INT UNSIGNED NOT NULL,
  -- 帖子内容，不能为空
  `content` TEXT NOT NULL,
  -- 发布时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 外键约束：关联 users 表的 id 字段，ON DELETE CASCADE 表示当 users 表中对应的记录被删除时，此表中的记录也会被删除
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- 社区评论表
CREATE TABLE `community_comment` (
  -- 评论ID，自增长，作为主键
  `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  -- 评论所属的帖子ID，不能为空，关联到 community_posts 表的 id 字段
  `post_id` INT UNSIGNED NOT NULL,
  -- 发表评论的用户ID，不能为空，关联到 users 表的 id 字段 (或 general_users, 取决于是否合并用户表)
  `user_id` INT UNSIGNED NOT NULL,
  -- 评论内容，不能为空
  `content` TEXT NOT NULL,
  -- 父评论ID, 可以为空, 如果不为空则表示此评论是对另一条评论的回复
  `parent_comment_id` INT UNSIGNED DEFAULT NULL,
  -- 评论时间，默认为当前时间
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  -- 更新时间，默认为当前时间，并在记录更新时自动更新
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- 外键约束：关联 community_posts 表的 id 字段
  FOREIGN KEY (`post_id`) REFERENCES `community_posts`(`id`) ON DELETE CASCADE,
  -- 外键约束：关联 users 表的 id 字段 (或 general_users 表)
  FOREIGN KEY (`user_id`) REFERENCES `general_users`(`id`) ON DELETE CASCADE, -- 或 users 表，如果进行了用户表合并
  -- 外键约束: 关联自身, 实现评论的回复功能.
  FOREIGN KEY (`parent_comment_id`) REFERENCES `community_comments`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;