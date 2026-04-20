/*
 Navicat Premium Dump SQL

 Source Server         : MySQL80
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : aiproject

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 26/03/2026 18:10:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `time` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `doctor_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description_of_the_illness` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES (4, '杨玉泉', '370784200309100000', '精神科', '2026-01-10', '下午', '张医生', NULL, NULL);
INSERT INTO `appointment` VALUES (6, '游泳圈', '37078400000', '神经内科', '2026-01-11', '上午', '崔丽英', NULL, NULL);
INSERT INTO `appointment` VALUES (7, '榆泉', '3707842003005545', '消化内科', '2026-01-12', '下午', '', NULL, NULL);
INSERT INTO `appointment` VALUES (9, '杨玉泉', '370784200309100666', '神经内科', '2026-01-15', '上午', '朱以诚', '885231379@qq.com', '头痛');
INSERT INTO `appointment` VALUES (10, '游泳圈', '3878123456', '心理医学科', '2026-01-15', '上午', '张晓红', '885231379@qq.com', '近期情绪低落，心理不适，有睡眠困扰，需要专业心理评估与支持。');
INSERT INTO `appointment` VALUES (12, '陈佳昊', '37078412121', '消化内科', '2026-01-18', '上午', '李景南', '16678667162@163.com', '肚子疼');
INSERT INTO `appointment` VALUES (13, '陈佳昊', '37078412121', '消化内科', '2026-01-17', '下午', '钱家鸣', '16678667162@163.com', '肚子疼');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone_number` int NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `id_number` int NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (7, 'str545454ing', 'string', NULL, 'string', 0, NULL, '2026-02-02 22:57:21', '2026-02-02 22:57:21', NULL, NULL);
INSERT INTO `employee` VALUES (6, '112222', 'str1112ing', NULL, 'string', 0, NULL, '2026-02-02 22:55:39', '2026-02-02 22:55:39', NULL, NULL);
INSERT INTO `employee` VALUES (5, 'ss0000', 'string', NULL, 'string', 0, NULL, '2026-01-29 23:03:09', '2026-01-29 23:03:09', NULL, NULL);
INSERT INTO `employee` VALUES (4, 'ss0000', 'string', NULL, 'string', 0, NULL, '2026-01-29 23:02:32', '2026-01-29 23:02:32', NULL, NULL);
INSERT INTO `employee` VALUES (3, '0000', 'string', NULL, 'string', 0, NULL, '2026-01-29 21:44:29', '2026-01-29 21:44:29', NULL, NULL);
INSERT INTO `employee` VALUES (2, '1023', 'string', NULL, 'string', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `employee` VALUES (1, 'string', 'string', NULL, 'string', 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `store_id` int NULL DEFAULT NULL,
  `room_id` bigint NULL DEFAULT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL COMMENT '0 已预约\n1 已完成\n2 已取消',
  `create_time` datetime NULL DEFAULT NULL,
  `remark1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预约名称',
  `remark2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reservation
-- ----------------------------
INSERT INTO `reservation` VALUES (1, 1, NULL, 1, '2026-03-15 10:08:29', '2026-03-15 10:08:29', 0, '2026-03-15 18:08:49', NULL, NULL);
INSERT INTO `reservation` VALUES (2, 0, NULL, 107, '2026-03-16 09:00:00', '2026-03-16 12:00:00', 0, '2026-03-15 20:52:54', NULL, NULL);
INSERT INTO `reservation` VALUES (3, 2, NULL, 3, '2026-03-15 22:22:17', '2026-03-15 22:33:20', 0, '2026-03-15 22:22:28', NULL, NULL);
INSERT INTO `reservation` VALUES (4, 18, NULL, 0, '2026-03-16 02:46:05', '2026-03-16 02:46:05', 0, '2026-03-16 10:50:37', 'string', 'string');
INSERT INTO `reservation` VALUES (5, 18, NULL, 2, '2026-03-16 02:46:05', '2026-03-16 02:46:05', 0, '2026-03-16 13:35:39', 'string', 'string');
INSERT INTO `reservation` VALUES (6, 18, NULL, 2, '2026-03-16 02:46:05', '2026-03-16 02:46:05', 0, '2026-03-16 13:35:44', 'string', 'string');
INSERT INTO `reservation` VALUES (7, 18, NULL, 2, '2026-03-16 02:46:05', '2026-03-16 02:46:05', 0, '2026-03-16 13:35:50', 'string', 'string');
INSERT INTO `reservation` VALUES (8, 0, 1, 1, '2026-03-17 09:00:00', '2026-03-17 12:00:00', 0, '2026-03-16 22:13:43', '', '');
INSERT INTO `reservation` VALUES (9, 0, 1, 1, '2026-03-18 07:00:00', '2026-03-18 09:00:00', 0, '2026-03-17 22:18:06', 'yyq', '15069649986');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `store_id` bigint NULL DEFAULT NULL,
  `room_type_id` bigint NULL DEFAULT NULL,
  `capacity` int NULL DEFAULT NULL,
  `room_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL COMMENT '0空闲  \n 1使用中\n   2维护',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `REMARK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES (1, 1, 1, NULL, '沉浸式小黑屋1', 1, '全封闭隔音设计，零光污染，专为深度备考（考研/考公/法考）打造，进入即“心流”', '0', NULL);
INSERT INTO `room` VALUES (2, 1, 2, NULL, '阳光景观区1', 1, '临窗开阔视野，自然光照明，允许轻声交流，适合创意写作、自由办公及阅读。', '0', NULL);
INSERT INTO `room` VALUES (3, 1, 3, NULL, '智能研讨室1', 0, '配备电子白板、高清投屏及AI会议助手，支持小组面试模拟、项目路演，自动记录会议纪要。', '0', NULL);
INSERT INTO `room` VALUES (4, 1, 4, NULL, ' VIP尊享舱1', 0, '独立新风系统、人体工学椅、专属储物柜，提供定制香氛与助眠服务，极致私密与舒适。', '0', NULL);
INSERT INTO `room` VALUES (5, 1, 5, NULL, '能量休憩舱1', 0, '内置智能唤醒灯光与白噪音，提供15-30分钟的高效小睡空间，快速恢复精力。', '0', NULL);
INSERT INTO `room` VALUES (6, 2, 1, NULL, '沉浸式小黑屋2', 0, '全封闭隔音设计，零光污染，专为深度备考（考研/考公/法考）打造，进入即“心流”', '0', NULL);
INSERT INTO `room` VALUES (7, 2, 2, NULL, '阳光景观区2', 0, '临窗开阔视野，自然光照明，允许轻声交流，适合创意写作、自由办公及阅读。', '0', NULL);
INSERT INTO `room` VALUES (8, 2, 3, NULL, '智能研讨室2', 0, '配备电子白板、高清投屏及AI会议助手，支持小组面试模拟、项目路演，自动记录会议纪要。', '0', NULL);
INSERT INTO `room` VALUES (9, 2, 4, NULL, 'VIP尊享舱2', 0, '独立新风系统、人体工学椅、专属储物柜，提供定制香氛与助眠服务，极致私密与舒适。', '0', NULL);
INSERT INTO `room` VALUES (10, 2, 5, NULL, '能量休憩舱2', 0, '内置智能唤醒灯光与白噪音，提供15-30分钟的高效小睡空间，快速恢复精力。', '0', NULL);
INSERT INTO `room` VALUES (11, 3, 1, NULL, '沉浸式小黑屋1', 0, '全封闭隔音设计，零光污染，专为深度备考（考研/考公/法考）打造，进入即“心流”', '0', NULL);
INSERT INTO `room` VALUES (12, 3, 2, NULL, '阳光景观区1', 0, '临窗开阔视野，自然光照明，允许轻声交流，适合创意写作、自由办公及阅读。', '0', NULL);
INSERT INTO `room` VALUES (13, 3, 3, NULL, '智能研讨室1', 0, '配备电子白板、高清投屏及AI会议助手，支持小组面试模拟、项目路演，自动记录会议纪要。', '0', NULL);
INSERT INTO `room` VALUES (14, 3, 4, NULL, 'VIP尊享舱1', 0, '独立新风系统、人体工学椅、专属储物柜，提供定制香氛与助眠服务，极致私密与舒适。', '0', NULL);
INSERT INTO `room` VALUES (15, 3, 5, NULL, '能量休憩舱1', 0, '内置智能唤醒灯光与白噪音，提供15-30分钟的高效小睡空间，快速恢复精力。', '0', NULL);
INSERT INTO `room` VALUES (16, 4, 1, NULL, '沉浸式小黑屋1', 0, '全封闭隔音设计，零光污染，专为深度备考（考研/考公/法考）打造，进入即“心流”', '0', NULL);
INSERT INTO `room` VALUES (17, 4, 2, NULL, '阳光景观区1', 0, '临窗开阔视野，自然光照明，允许轻声交流，适合创意写作、自由办公及阅读。', '0', NULL);
INSERT INTO `room` VALUES (18, 4, 3, NULL, '智能研讨室1', 0, '配备电子白板、高清投屏及AI会议助手，支持小组面试模拟、项目路演，自动记录会议纪要。', '0', NULL);
INSERT INTO `room` VALUES (19, 4, 4, NULL, 'VIP尊享舱1', 0, '独立新风系统、人体工学椅、专属储物柜，提供定制香氛与助眠服务，极致私密与舒适。', '0', NULL);
INSERT INTO `room` VALUES (20, 4, 5, NULL, '能量休憩舱1', 0, '内置智能唤醒灯光与白噪音，提供15-30分钟的高效小睡空间，快速恢复精力。', '0', NULL);

-- ----------------------------
-- Table structure for room_type
-- ----------------------------
DROP TABLE IF EXISTS `room_type`;
CREATE TABLE `room_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `capacity` int NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room_type
-- ----------------------------
INSERT INTO `room_type` VALUES (1, '沉浸式小黑屋', 1, 15.00, '全封闭隔音设计，零光污染，专为深度备考（考研/考公/法考）打造，进入即“心流”');
INSERT INTO `room_type` VALUES (2, '阳光景观区', 2, 25.00, '临窗开阔视野，自然光照明，允许轻声交流，适合创意写作、自由办公及阅读。');
INSERT INTO `room_type` VALUES (3, '智能研讨室', 8, 35.00, '配备电子白板、高清投屏及AI会议助手，支持小组面试模拟、项目路演，自动记录会议纪要。');
INSERT INTO `room_type` VALUES (4, ' VIP尊享舱', 1, NULL, '独立新风系统、人体工学椅、专属储物柜，提供定制香氛与助眠服务，极致私密与舒适。');
INSERT INTO `room_type` VALUES (5, '能量休憩舱', 1, NULL, '内置智能唤醒灯光与白噪音，提供15-30分钟的高效小睡空间，快速恢复精力。');

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `open_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `close_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES (1, '智学空间青农', '青岛市城阳区', '青岛农业大学', '08:00', '23:00');
INSERT INTO `store` VALUES (2, '智学空间青岛CBD店', '青岛市市南区', '青岛站', '08:00', '23:00');
INSERT INTO `store` VALUES (3, '智学空间城阳社区店', '青岛市城阳区', '大润发', '08:00', '23:00');
INSERT INTO `store` VALUES (4, '高密智学空间', '潍坊市高密市', '高密中骏世界城', '07:00', '24:00');

-- ----------------------------
-- Table structure for user_entity
-- ----------------------------
DROP TABLE IF EXISTS `user_entity`;
CREATE TABLE `user_entity`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `balance` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_entity
-- ----------------------------
INSERT INTO `user_entity` VALUES (16, 'yyq0123456', '5c02f67a1da6b9b24c87a914d44c7470', '885231379@qq.com', NULL, '2026-01-21 22:09:29', '2026-01-21 22:09:29', NULL, NULL);
INSERT INTO `user_entity` VALUES (17, 'yyq123', 'e10adc3949ba59abbe56e057f20f883e', '885231379@qq.com', NULL, '2026-01-22 09:29:36', '2026-01-22 09:29:36', NULL, NULL);
INSERT INTO `user_entity` VALUES (18, 'yyq0910', '7df15eafc447c04b1ee8ee989f3f7044', '885231379@qq.com', NULL, '2026-03-15 13:28:29', '2026-03-15 13:28:29', NULL, NULL);

-- ----------------------------
-- Table structure for wx_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信用户 openid',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `balance` decimal(10, 2) NULL DEFAULT NULL COMMENT '余额',
  `nickname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `uk_openid`(`openid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '微信用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wx_user
-- ----------------------------
INSERT INTO `wx_user` VALUES (1, '1', '1', '1', 1.00, '1', '1', '1', '2026-03-14 19:20:11', '2026-03-14 19:20:14');
INSERT INTO `wx_user` VALUES (2, '123', '张三', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wx_user` VALUES (3, 'oQ7lg3b6lqWvMIlT0T9JkULs5hJM', NULL, NULL, NULL, NULL, NULL, NULL, '2026-03-14 20:57:25', NULL);

SET FOREIGN_KEY_CHECKS = 1;
