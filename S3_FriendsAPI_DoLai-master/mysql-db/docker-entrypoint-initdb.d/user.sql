USE `friends-management`;

START TRANSACTION;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(256) NOT NULL,
  `created_at` int(11) DEFAULT NULL,
  `updated_at` int(11) DEFAULT NULL,
  `is_deleted` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `relationship`;
CREATE TABLE `relationship` (
  `user_one_id` int(10) unsigned NOT NULL,
  `user_two_id` int(10) unsigned NOT NULL,
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0',
  UNIQUE KEY `unique_users_id` (`user_one_id`,`user_two_id`),
  KEY `user_two_id` (`user_two_id`),
  CONSTRAINT `relationship_ibfk_1` FOREIGN KEY (`user_one_id`) REFERENCES `users` (`id`),
  CONSTRAINT `relationship_ibfk_2` FOREIGN KEY (`user_two_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users`(
    `id`,
    `email`,
    created_at,
     updated_at,
     is_deleted
)
VALUES
(1,"anh.tran@s3corp.com.vn",1574218496,1574218496,0),
(2,"chi.vo@s3corp.com.vn",1574218496,1574218496,0),
(2,"do.lai@s3corp.com.vn",1574218496,1574218496,0),
(2,"thinh.pham@s3corp.com.vn",1574218496,1574218496,0),
(3,"chinh.nguyen@s3corp.com.vn",1574218496,1574218496,0);

COMMIT;