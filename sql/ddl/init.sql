-- --------------------------------------------------------
-- IP:                      192.168.85.128
-- server version:          5.7.33-0ubuntu0.16.04.1 - (Ubuntu)
-- OS:                      Linux
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


DROP DATABASE IF EXISTS `chatbot`;
CREATE DATABASE IF NOT EXISTS `chatbot` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `chatbot`;

DROP TABLE IF EXISTS `agents`;
CREATE TABLE IF NOT EXISTS `agents` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Auto-incrementing primary key',
    `name` varchar(128) NOT NULL COMMENT 'Display name of the agent',
    `prompt_id` int(11) NOT NULL COMMENT 'Owning prompt id',
    `kb_ids` varchar(128) DEFAULT NULL COMMENT 'Owning knowledge base ids',
    `description` text COMMENT 'Detailed description of the agent''s purpose',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modification time',
    `is_active` tinyint(1) DEFAULT '1' COMMENT 'Whether the agent is available for use',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uc_agent_name` (`name`) COMMENT 'Ensure agent names are unique'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Stores metadata and configurations for AI agents';

DROP TABLE IF EXISTS `conversations`;
CREATE TABLE IF NOT EXISTS `conversations` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Auto-incrementing primary key',
    `user_id` varchar(255) DEFAULT NULL COMMENT 'User identifier (nullable for anonymous users)',
    `title` text COMMENT 'Conversation title/summary (e.g., first message excerpt)',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation timestamp',
    `is_deleted` tinyint(1) DEFAULT '0' COMMENT 'Conversation deletion (soft deletion)',
    PRIMARY KEY (`id`),
    KEY `idx_conversations_user` (`user_id`),
    KEY `idx_conversations_created` (`created_at`),
    KEY `idx_conversations_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Stores AI conversation sessions';

DROP TABLE IF EXISTS `feedbacks`;
CREATE TABLE IF NOT EXISTS `feedbacks` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Auto-incrementing primary key',
    `message_id` bigint(20) NOT NULL COMMENT 'References assistant message being rated',
    `rating` smallint(6) DEFAULT NULL COMMENT 'Numeric rating (1-5 scale)',
    `comment` text COMMENT 'Optional qualitative feedback text',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Feedback timestamp',
    PRIMARY KEY (`id`),
    KEY `idx_feedbacks_message` (`message_id`),
    KEY `idx_feedbacks_rating` (`rating`),
    KEY `idx_feedbacks_created` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Stores user feedback on AI responses';

DROP TABLE IF EXISTS `knowledge_bases`;
CREATE TABLE IF NOT EXISTS `knowledge_bases` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Auto-incrementing primary key',
    `name` varchar(255) NOT NULL COMMENT 'Index name of the knowledge base',
    `description` text COMMENT 'Description of the knowledge base contents',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modification time',
    `embedding_model` varchar(100) DEFAULT NULL COMMENT 'Identifier for the embedding model used',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uc_kb_name` (`name`) COMMENT 'Unique KB names per agent',
    KEY `idx_knowledge_bases_embedding_model` (`embedding_model`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Metadata about knowledge repositories used by agents';


DROP TABLE IF EXISTS `messages`;
CREATE TABLE IF NOT EXISTS `messages` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Auto-incrementing primary key',
    `conversation_id` bigint(20) NOT NULL COMMENT 'Associated conversation ID',
    `role` varchar(10) NOT NULL COMMENT 'Message origin: ''user'' or ''assistant''',
    `content` text NOT NULL COMMENT 'The actual message text content',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation timestamp',
    `model` varchar(50) DEFAULT NULL COMMENT 'AI model identifier used for response',
    PRIMARY KEY (`id`),
    KEY `idx_messages_conversation` (`conversation_id`),
    KEY `idx_messages_created` (`created_at`),
    KEY `idx_messages_role` (`role`),
    KEY `idx_messages_model` (`model`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Stores individual messages in conversations';

DROP TABLE IF EXISTS `prompts_en`;
CREATE TABLE IF NOT EXISTS `prompts_en` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Auto-incrementing primary key',
    `name` varchar(255) NOT NULL COMMENT 'Display name of the prompts',
    `content` text NOT NULL COMMENT 'prompt template',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modification time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uc_prompt_name` (`name`) COMMENT 'Unique prompt names per agent',
    KEY `idx_prompts_created_desc` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Stores prompt templates associated with agents';


/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
