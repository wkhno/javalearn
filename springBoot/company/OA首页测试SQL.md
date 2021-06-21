```sql
select * from oa_crm_customer WHERE deleted=0 and  (create_date BETWEEN '2021-05-01' AND '2021-5-26');

select * from oa_crm_customer WHERE deleted=0 and customer_type like '超级大客户'   ;

select * from oa_crm_customer WHERE deleted=0 and  customer_type_id = 0 ;

select * from oa_crm_customer WHERE deleted=0 and customer_type not like '%超级大客户%'    ;
                   /* AND (create_date BETWEEN '2020-05-01' AND '2020-5-30'); */
select * from oa_crm_customer WHERE deleted=0 and customer_source like '%独立开发%';

select * from oa_crm_customer WHERE deleted=0  and follow_up_status like '成交' and  (create_date BETWEEN '2021-05-01' AND '2021-5-26');

select count(*) from oa_crm_customer WHERE deleted=0  and follow_up_status like '成交';

# follow_up_status

SELECT COUNT( 1 )
 FROM oa_crm_customer
 WHERE deleted=0 AND (  customer_type_id = 0);

# S,A,B,C
SELECT COUNT( 1 )
 FROM oa_crm_customer
 WHERE deleted=0 AND (create_date BETWEEN '2021-05-01' AND '2021-05-26')
```





```sql
SELECT count(*) FROM oa_crm_customer where datediff(create_time,'2020-05-28')>=0 and  datediff(create_time,'2021-01-31')<=0


SELECT self.*
        FROM oa_company_conf conf
        INNER JOIN qs_company_self_employed self ON conf.ssc_company_id = self.company_id
        WHERE self.add_status = 1
        AND self.grant_status = 1
        AND self.deleted = 0 and self.status =3
				
				
				select distinct(customer_type_id),customer_type from oa_crm_customer
				
				
				select count(*) from oa_crm_customer where customer_type_id in(-1,-2)
				
				
				
					select distinct(customer_source_id),customer_source from oa_crm_customer
				
				
				select count(*) from oa_crm_customer where customer_source_id=4
				
				
				
				select count(*) from oa_crm_customer where DATE_FORMAT(create_time, '%Y-%m') in ('2020-12') and customer_source_id=0 and earnable in (1, 2)
				
				
				select 17380+3648+1386+2233
				
				
				
				 SELECT DATE_FORMAT(create_time, '%Y-%m') AS MONTH,
        count(*) as data
        FROM oa_crm_customer
        WHERE 1=1 AND DATE_FORMAT(create_time, '%Y-%m') IN ('2020-05','2020-06','2020-07','2020-08','2020-09','2020-10','2020-11','2020-12','2021-01','2021-02') GROUP BY MONTH
				
				
				 SELECT DATE_FORMAT(create_time, '%Y-%m') AS MONTH,
        count(*) as data
        FROM oa_crm_clue
        WHERE deleted =0 AND DATE_FORMAT(create_time, '%Y-%m') IN ('2020-09','2020-10','2020-11','2020-12','2021-01','2021-02') GROUP BY MONTH
        
        SELECT (count(distinct company_name)) FROM oa_crm_customer where datediff(create_time,'2021-05-01')>=0 and  datediff(create_time,'2021-05-27')<=0 and customer_type_id=0
```

针对primary_key duplicate 实现批量insertOrUpdate MYSQL

```sql
insert into oa_user_weekly_conf (select distinct(user_id),15 from oa_user_roles where user_id like '%c%') ON DUPLICATE KEY UPDATE conf_id=13;
```



```sql
SELECT * FROM `lx_files` where DATE_FORMAT(update_time,'%Y-%m-%d')<'2019-08-01'


INSERT INTO `lanxi_dev_001`.`oa_weekly_conf`(`id`, `conf_name`, `end_week_day`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (2, '普通员工', 6, '18:00:00', 'carpediem', 'carpediem', '2021-05-19 10:55:45', '2021-05-19 10:55:50');
INSERT INTO `lanxi_dev_001`.`oa_weekly_conf`(`id`, `conf_name`, `end_week_day`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (3, '客服部', 6, '15:00:00', 'carpediem', 'JiangMingXin', '2021-05-20 13:33:40', '2021-05-21 10:23:09');
INSERT INTO `lanxi_dev_001`.`oa_weekly_conf`(`id`, `conf_name`, `end_week_day`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (10, '产品技术中心', 7, '15:00:00', 'carpediem', 'carpediem', '2021-05-20 14:58:38', '2021-05-21 16:57:41');
INSERT INTO `lanxi_dev_001`.`oa_weekly_conf`(`id`, `conf_name`, `end_week_day`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (11, '商务部', 5, '23:59:59', 'carpediem', 'carpediem', '2021-05-20 15:03:55', '2021-05-20 15:03:55');
INSERT INTO `lanxi_dev_001`.`oa_weekly_conf`(`id`, `conf_name`, `end_week_day`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (13, 'cck', 5, '23:59:59', 'carpediem', 'carpediem', '2021-05-20 15:04:22', '2021-05-20 15:04:22');
INSERT INTO `lanxi_dev_001`.`oa_weekly_conf`(`id`, `conf_name`, `end_week_day`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (14, '产品部', 1, '20:00:00', 'carpediem', '', '2021-05-21 10:25:36', '2021-05-21 10:25:36');
INSERT INTO `lanxi_dev_001`.`oa_weekly_conf`(`id`, `conf_name`, `end_week_day`, `end_time`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (15, '研发中心', 6, '23:10:00', 'carpediem', 'carpediem', '2021-05-21 10:36:43', '2021-05-21 10:36:43');

DROP TABLE IF EXISTS `oa_weekly_conf`;
CREATE TABLE if not exists oa_weekly_conf
(
    id          INT ( 11 ) NOT NULL AUTO_INCREMENT,
    conf_name   VARCHAR(32) DEFAULT '' NOT NULL unique COMMENT '角色名称',
    end_week_day int(11) not null COMMENT '截止日期',
    end_time    TIME                   not null COMMENT '截止时间',
    create_by   VARCHAR(64) DEFAULT '' COMMENT '创建者',
    update_by   VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time datetime    DEFAULT NULL,
    update_time datetime    DEFAULT NULL,
    PRIMARY KEY (id) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8mb4 COMMENT = 'OA周报配置';

DROP TABLE IF EXISTS `oa_user_weekly_conf`;
CREATE TABLE if not exists `oa_user_weekly_conf`
(
    `user_id` VARCHAR(64) DEFAULT '' NOT NULL unique COMMENT '企业微信成员UserID。对应管理端的帐号',
    `conf_id` INT ( 11 ) NOT NULL COMMENT '周报配置id',
    UNIQUE KEY `unique_user_id` (`user_id`)
) ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8mb4 COMMENT = 'OA用户周报配置关联表';

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
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('FanQinLingYan', 3);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('FanQinLingYan01', 3);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('GaoXiaQi', 3);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('GuoWeiQi', 3);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('hedanqin', 3);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('HeDanQing', 3);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('qiumo', 3);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('rose', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('TongJia', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('vicky', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('XiongXueJiang', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('XuWenTao', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ZhaoChang', 2);
INSERT INTO `lanxi_dev_001`.`oa_user_weekly_conf`(`user_id`, `conf_id`) VALUES ('ZhengMinTing', 2);


alter table oa_weekly add column annex_url varchar(2048) default NULL comment '附件';
alter table oa_weekly add column annex_url DATETIME comment '截止时间';
```

