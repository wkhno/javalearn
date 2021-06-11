```sql
CREATE TABLE oa_weekly_conf (
	id INT ( 11 ) NOT NULL AUTO_INCREMENT,
	conf_name VARCHAR ( 32 ) DEFAULT '' NOT NULL unique COMMENT '角色名称',
	end_weekday int(11) not null COMMENT '截止日期',
	end_time TIME not null COMMENT '截止时间',
	create_by VARCHAR ( 64 ) DEFAULT '' COMMENT '创建者',
	update_by  VARCHAR ( 64 ) DEFAULT '' COMMENT '更新者',
  create_time   datetime         DEFAULT NULL,
  update_time   datetime         DEFAULT NULL,
	PRIMARY KEY (id) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8mb4 COMMENT = 'OA周报配置';

INSERT INTO `oa_weekly_conf`(`id`, `conf_name`, `end_day`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (2, '普通员工', 6, '18:00:00', 'carpediem', 'carpediem', '2021-05-19 10:55:45', '2021-05-19 10:55:50');
INSERT INTO `oa_weekly_conf`(`id`, `conf_name`, `end_day`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (1, '部门管理员', 7, '18:00:00', 'JiangMingXin', 'JiangMingXin', '2021-05-19 10:57:49', '2021-05-19 10:57:51');


CREATE TABLE `oa_user_weekly_conf` (
	`user_id` VARCHAR ( 64 ) DEFAULT '' NOT NULL unique COMMENT '企业微信成员UserID。对应管理端的帐号',
	`conf_id` INT ( 11 ) NOT NULL COMMENT '周报配置id'，
    UNIQUE KEY `user_id` (`user_id`)
) ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8mb4 COMMENT = 'OA用户周报配置关联表';

INSERT INTO `oa_user_weeklyconf`(`user_id`, `conf_id`) VALUES ('carpediem', 2);
INSERT INTO `oa_user_weeklyconf`(`user_id`, `conf_id`) VALUES ('XiongXueJiang', 2);
INSERT INTO `oa_user_weeklyconf`(`user_id`, `conf_id`) VALUES ('WangYiFeng', 1);
```

```sql
CREATE TABLE oa_weekly_conf (
	id INT ( 11 ) NOT NULL AUTO_INCREMENT,
	conf_name VARCHAR ( 32 ) DEFAULT '' NOT NULL unique COMMENT '角色名称',
	end_weekday int(11) not null COMMENT '截止日期',
	end_time TIME not null COMMENT '截止时间',
	create_by VARCHAR ( 64 ) DEFAULT '' COMMENT '创建者',
	update_by  VARCHAR ( 64 ) DEFAULT '' COMMENT '更新者',
  create_time   datetime         DEFAULT NULL,
  update_time   datetime         DEFAULT NULL,
	PRIMARY KEY (id) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8mb4 COMMENT = 'OA周报配置';

INSERT INTO `oa_weekly_conf`(`id`, `conf_name`, `end_weekday`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (2, '普通员工', 6, '18:00:00', 'carpediem', 'carpediem', '2021-05-19 10:55:45', '2021-05-19 10:55:50');
INSERT INTO `oa_weekly_conf`(`id`, `conf_name`, `end_weekday`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (1, '部门管理员', 7, '18:00:00', 'JiangMingXin', 'JiangMingXin', '2021-05-19 10:57:49', '2021-05-19 10:57:51');


CREATE TABLE `oa_user_weekly_conf` (
	`user_id` VARCHAR ( 64 ) DEFAULT '' NOT NULL unique COMMENT '企业微信成员UserID。对应管理端的帐号',
	`conf_id` INT ( 11 ) NOT NULL COMMENT '周报配置id',
    UNIQUE KEY `unique_user_id` (`user_id`)
) ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8mb4 COMMENT = 'OA用户周报配置关联表';

INSERT INTO `oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('carpediem', 2);
INSERT INTO `oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('XiongXueJiang', 2);
INSERT INTO `oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('WangYiFeng', 1);


SELECT id,conf_name,end_weekday,end_time,create_by,update_by,create_time,update_time FROM oa_weekly_conf WHERE (id = ?) limit 1 

 SELECT user_id,conf_id FROM oa_weekly_conf WHERE (conf_id = 1) 

insert into oa_user_weekly_conf (user_id,conf_id) (select distinct user_id ,3 as conf_id from oa_user where user_id like '%q%' and user_id!='92b69fde0fb26fb16e10828da986535c' and user_id!='ChenGuanFeng' and user_id!='ChiYuFeiHong' and user_id!='DingDaFaCai')

SELECT id,user_id,name,mobile,position,gender,email,is_leader,avatar,thumb_avatar,alias,status,extattr,qr_code,address,hide_mobile,telephone,main_department,enable,create_time,update_time FROM oa_user WHERE (name LIKE %x%) 


INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('92b69fde0fb26fb16e10828da986535c', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('CaiBoHan', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('CaiHongYang', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('carpediem', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ChenChunYuan', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ChengDongHang', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ChenGuanFeng', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ChenJiaHan', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ChenJin', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ChenRui', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ChiYuFeiHong', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('claire', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('csLiZhengHan', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('CuiJianZhong', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('DingDaFaCai', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('LiangCheng', 1);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('LuXiuTing', 1);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('mr.cui', 1);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('rachel', 1);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('rose', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('TongJia', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('vicky', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('WangYiFeng', 1);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('XiongXueJiang', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('XuWenTao', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ZhaoChang', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ZhengMinTing', 2);


SELECT id,user_id,name,mobile,position,gender,email,is_leader,avatar,thumb_avatar,alias,status,extattr,qr_code,address,hide_mobile,telephone,main_department,enable,create_time,update_time FROM oa_user WHERE (user_id = 'XuZhen') limit 1 

update oa_user set enable=1 where user_id='Xuzhen'

select distinct(user_id) from oa_user where user_id not in (select user_id from oa_user_weekly_conf) and enable=1
```

```java
LambdaQueryWrapper<OaUser> userWrapper = new LambdaQueryWrapper<OaUser>()
                    .eq(OaUser::getEnable, EARNABLE)
                    .ne(OaUser::getCreateTime, oaVersionMapper.selectOne(versionWrapper).getVersionTime());
List<OaUser> uncheckedUsers = oaUserMapper.selectList(userWrapper);
            uncheckedUsers.forEach(oauser -> {
                oauser.setEnable(DISEARNABLE);
                oaUserMapper.updateById(oauser);
            });
```

非当前版本不可用 更新某个字段的方法
