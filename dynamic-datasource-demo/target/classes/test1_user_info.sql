DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键无意义',
  `user_name` varchar(10) NOT NULL DEFAULT '' COMMENT '用户名称',
  `user_age` int(11) NOT NULL DEFAULT '0' COMMENT '用户年纪',
  `address` varchar(50) NOT NULL DEFAULT '' COMMENT '地址信息',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  `remarks` varchar(50) NOT NULL DEFAULT 'master' COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

INSERT INTO `user_info` VALUES ('1', 'test1', '20', 'master', '2016-06-05 19:32:42', 'test1');
INSERT INTO `user_info` VALUES ('2', 'test2', '22', 'master', '2016-06-05 19:35:40', 'test1');
