sql查询两个日期之间 create_time 类型 datetime

select count(code) as '统计',create_time as '订单时间' from sp_orders where **datediff(create_time,'2017-06-06')>=0 and datediff(create_time,'2017-09-09')<=0** group by month(create_time)



2. DATE获取前六个月的年份+月份  坑：用SimpleDateFormat setTime用月份}、+日期 不要用时间.getmillis容易出问题

/**
      * 根据年月获取当月最后一天
            * @param yearmonth yyyy-MM
            * @return yyyy-MM-dd
                  * @throws ParseException
                      */
                       public static String getLastDayOfMonth(String yearmonth) {
                      try {
                       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                    Date dd = format.parse(yearmonth);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dd);
                    int cc=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    String result = yearmonth+"-"+cc;
                    return result;
                       } catch (ParseException e) {
                  e.printStackTrace();
                       }
                       return null;
                       }

```java
// calendar.setTimeInMillis(date.getTime());
```

这种在31号这种末尾的日期容易出错