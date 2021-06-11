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

