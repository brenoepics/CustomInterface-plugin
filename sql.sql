CREATE TABLE IF NOT EXISTS `items_timed_rewards` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chance` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL DEFAULT '0',
  `credits` int NOT NULL DEFAULT '0',
  `points_type` int NOT NULL DEFAULT '0',
  `points` int NOT NULL DEFAULT '0',
  `duckets` int NOT NULL DEFAULT '0',
  `badge` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
);

INSERT INTO `achievements` (`name`, `category`, `reward_amount`, `points`) VALUES ('TimedReward', 'events', 0, 0);

ALTER TABLE `permissions` ADD COLUMN `cmd_reload_timed_rewards` ENUM('0','1') NOT NULL DEFAULT '0';
ALTER TABLE `permissions` ADD COLUMN `cmd_rare_value` ENUM('0','1') NOT NULL DEFAULT '1';
ALTER TABLE `permissions` ADD COLUMN `cmd_reload_rare_value` ENUM('0','1') NOT NULL DEFAULT '0';
