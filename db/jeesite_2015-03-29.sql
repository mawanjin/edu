# ************************************************************
# Sequel Pro SQL dump
# Version 3408
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.21)
# Database: jeesite
# Generation Time: 2015-03-29 15:34:48 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table edu_indeximg
# ------------------------------------------------------------

DROP TABLE IF EXISTS `edu_indeximg`;

CREATE TABLE `edu_indeximg` (
  `id` varchar(40) NOT NULL,
  `img` varchar(200) DEFAULT NULL,
  `porder` tinyint(4) DEFAULT NULL COMMENT '顺序 数字越大排越高',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标记',
  `remarks` varchar(80) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者(关联到用户表)',
  `update_by` varchar(40) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页顶部图片';

LOCK TABLES `edu_indeximg` WRITE;
/*!40000 ALTER TABLE `edu_indeximg` DISABLE KEYS */;

INSERT INTO `edu_indeximg` (`id`, `img`, `porder`, `del_flag`, `remarks`, `create_by`, `update_by`, `create_date`, `update_date`, `status`, `name`)
VALUES
	('174a87aa094e4c82acbfee3372fe0e62','f04bc8ea012b4f71bc04d9fd58345b15.png',1,'0','sdfas','1','1','2015-03-29 23:15:14','2015-03-29 23:15:14',0,'afsdf'),
	('48cf06bbd6af49f58ccecc1a15989018',NULL,13,'1','sdfds','1','1','2015-03-29 23:09:21','2015-03-29 23:11:59',1,'fsdfsadf'),
	('6eaf62d8089742ba8f247f8aa9810236',NULL,0,'1','dsfa','1','1','2015-03-29 23:08:58','2015-03-29 23:08:58',0,'safs,1'),
	('85fe6269c9c140999a44b86c19b4738c',NULL,0,'1','fasdf','1','1','2015-03-29 23:01:04','2015-03-29 23:11:57',1,'asf');

/*!40000 ALTER TABLE `edu_indeximg` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
