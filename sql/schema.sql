/*
Navicat MySQL Data Transfer

Source Server         : Master
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2016-07-03 02:10:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `idex_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_start_time` (`start_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1', '1000元秒杀iphone6', '100', '2016-07-03 02:01:36', '2016-07-03 20:00:57', '2016-08-10 02:01:28');
INSERT INTO `seckill` VALUES ('2', '500元秒杀ipad2', '200', '2016-07-03 02:02:16', '2016-07-03 02:02:03', '2016-08-18 02:02:07');
INSERT INTO `seckill` VALUES ('3', '300元秒杀红米', '300', '2016-07-03 02:03:07', '2016-07-03 02:02:58', '2016-07-20 02:03:00');

-- ----------------------------
-- Table structure for success_killed
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号码',
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '状态标识 -1 无效 0:成功 1:已付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of success_killed
-- ----------------------------
