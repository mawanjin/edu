# ************************************************************
# Sequel Pro SQL dump
# Version 3408
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.21)
# Database: jeesite
# Generation Time: 2015-03-30 18:10:38 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table edu_user_relation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `edu_user_relation`;

CREATE TABLE `edu_user_relation` (
  `id` varchar(40) NOT NULL,
  `parent_id` varchar(40) DEFAULT NULL COMMENT ' 标题',
  `student_id` varchar(40) DEFAULT NULL COMMENT '登录名',
  PRIMARY KEY (`id`),
  KEY `edu_user_parent` (`parent_id`),
  CONSTRAINT `edu_user_parent` FOREIGN KEY (`parent_id`) REFERENCES `edu_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `edu_user_studnet` FOREIGN KEY (`id`) REFERENCES `edu_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教育用户关系表';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
