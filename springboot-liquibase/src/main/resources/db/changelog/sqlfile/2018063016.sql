ALTER TABLE `app`
ADD COLUMN `memo`  varchar(255) NULL COMMENT '备注' AFTER `app_name`;