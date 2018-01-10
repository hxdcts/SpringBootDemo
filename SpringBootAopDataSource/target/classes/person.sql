CREATE TABLE `t_person` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `birth_date` varchar(100) DEFAULT '',
  `sex` char(2) DEFAULT NULL,
  `phone_no` varchar(11) DEFAULT NULL,
  `update_dt` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;