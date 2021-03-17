DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'open_id',
  `skey` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'skey',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `last_visit_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `session_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'session_key',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '市',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '国',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint(11) NULL DEFAULT NULL COMMENT '性别',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

DROP TABLE IF EXISTS `balance`;
CREATE TABLE `balance`(
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) DEFAULT NULL  COMMENT '用户id',
  `payments` decimal(15,4) DEFAULT 0 COMMENT '金额',
  `kind` int DEFAULT NULL COMMENT '收/支',
  `type` int DEFAULT NULL COMMENT '种类',
  `context` varchar(255) DEFAULT NULL  COMMENT '内容',
  `date` date DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`id`) 
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`(
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int DEFAULT NULL COMMENT '种类',
  PRIMARY KEY (`id`) 
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;