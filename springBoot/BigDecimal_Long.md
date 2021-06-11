```java
if (BigDecimal.ZERO.compareTo(new BigDecimal(oaCrmCustomerHomeResponse.getTotalCustomerNum())) == 0) {
    oaCrmCustomerHomeResponse.setActiveRate(BigDecimalUtils.percent(BigDecimal.ZERO));
} else {
    oaCrmCustomerHomeResponse.setActiveRate(BigDecimalUtils.percent(BigDecimalUtils.safeDivide(new BigDecimal(oaCrmCustomerHomeResponse.getActiveCustomerNum()),
            new BigDecimal(oaCrmCustomerHomeResponse.getTotalCustomerNum()))));
}
```

数字不涉及小数金额的计算百分比

![image-20210527103917930](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210527103917930.png)

![image-20210527104521113](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210527104521113.png)

![image-20210527104703522](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210527104703522.png)



未加时间和数据筛选

```
1.第二行 可用客户数-数据来源除独立开发以外导入客户来源数据无法对应。
如来源设置为独立开发代理商总数增加，分类型未增加。
```



加时间和数据筛选



图表检查



![image-20210527110607513](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210527110607513.png)









2021-05：16 39 0 16

![image-20210527111003850](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210527111003850.png)





2021-05： 17 40 0 17

![image-20210527111417169](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210527111417169.png)

2.页面放置隔一段时间数据就会都刷不出来。

客户管理



3.统计数据外访数增加，图表中当前月外访数没有增加。统计不一致。（图表中外访数暂时为0）  OK





4.检查线索非需求明确不转客户   线索转客户正确。

![image-20210527113520122](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210527113520122.png)

![image-20210527113752185](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210527113752185.png)

5.跟进数有问题 后端逻辑：增加一条线索才认为跟进 与签到次数以及跟进时间无关。

![image-20210527114007617](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210527114007617.png)

跟进数和crm客户数，外访都增加了。

### 图表问题

6.跟进数与图表跟进数总和不符。

![跟进数与图表跟进数总和不符](C:\Users\admin\Desktop\跟进数与图表跟进数总和不符.png)

17380+3648+1390不等于22403.



9、查询所得1 图表显示4  ok

```sql
SELECT DATE_FORMAT(create_time, '%Y-%m') AS MONTH,
        IFNULL(count(*),0) as data
        FROM oa_crm_customer
        WHERE earnable in (1, 2)
				and customer_type='超级大客户'
        AND DATE_FORMAT(create_time, '%Y-%m') in ('2021-04')
```

10.经营中没有截止到昨天。 后端接口问题。个体户包含云纳税和个体工商户两条业务线，财务端可查看。





小程序测试：1. 2021-05 小程序数据展示与PC端不一致。后台接口问题

![image-20210528113849469](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210528113849469.png)

2.当前只要切换页面不用退出小程序或者PC登录 筛选条件都会重新初始化。

3.跟进数根据签到次数来，可能会出现线索转客户，两边签到次数不一致，或者签到次数重复计算的情况。

筛选条件没对上。

![image-20210528151445044](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210528151445044.png)