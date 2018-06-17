--创建数据库
CREATE DATABASE IF NOT EXISTS jeedev DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

--创建数据表
DROP TABLE IF EXISTS app;
CREATE TABLE `app` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '是否逻辑删除,默认否',
  `re_order` int(11) unsigned NOT NULL DEFAULT 0 COMMENT '排序值',
  `app_code` varchar(32) NOT NULL COMMENT '应用编号',
  `app_name` varchar(255) NOT NULL COMMENT '应用名称',
  PRIMARY KEY (`id`),
  KEY `uk_app_code` (`app_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用表';