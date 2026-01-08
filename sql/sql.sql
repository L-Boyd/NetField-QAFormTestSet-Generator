create database if not exists qa_generator;
use qa_generator;

CREATE TABLE `chat_memory` (
                               `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                               `session_id` VARCHAR(255) NOT NULL COMMENT '会话ID',
                               `content` TEXT NOT NULL COMMENT '消息内容',
                               PRIMARY KEY (`id`),
                               INDEX `idx_session_id` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='对话记忆表';
