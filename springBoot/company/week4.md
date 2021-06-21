### Calendar的DAY_OF_MONTH, DAY_OF_YEAR, DATE的区别

```java
cal1.add(Calendar.DAY_OF_MONTH,1);  
cal1.add(Calendar.DAY_OF_YEAR,1);  
cal1.add(Calendar.DATE,1);
```

就单纯的add操作结果都一样，因为都是将日期+1
就没有区别说是在月的日期中加1还是年的日期中加1
但是Calendar设置DAY_OF_MONTH和DAY_OF_YEAR的目的不是用来+1
将日期加1，这通过cal1.add(Calendar.DATE,1)就可以实现.

主要用来区分获取当前月第几天，当前年第几天。



### 获取特定年月第一天

```java

 public static Date getMonthFirstDay(Integer year,Integer month){
    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, year);
    c.set(Calendar.DAY_OF_MONTH,0);
    c.set(Calendar.MONTH, month);
    c.set(Calendar.DAY_OF_MONTH,
    c.getActualMinimum(Calendar.DAY_OF_MONTH));  
    c.set(Calendar.HOUR_OF_DAY,0);
    c.set(Calendar.MINUTE,0);
    c.set(Calendar.SECOND,0);
    return  c.getTime();

```



### 获取指定日期的上周的第一天、最后一天，即周一和周日

```java
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
Calendar cld = Calendar.getInstance(Locale.CHINA); //使用中国时区得到一个日历
cld.setTime(sdf.parse("2019-12-22"));
cld.add(Calendar.DATE, -7);
cld.setFirstDayOfWeek(Calendar.MONDAY); //以周一为首日

cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //周一
System.out.println(sdf.format(cld.getTime()));

cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //周日
System.out.println(sdf.format(cld.getTime()));
```

### 获取今天年月日

```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Date date = new Date();
String today = sdf.format(new Date());
System.out.println("今天："+today); //获取今天的年月日，2019-09-11
```

**年月日 时分秒的格式：yyyy-MM-dd HH:mm:ss**

### 获取指定日期所属月份最后一天

//指定日期字符串--转换为--日期格式Calendar
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Date thisDate = sdf.parse("2019-09-15");
Calendar cld = Calendar.getInstance();
cld.setTime(thisDate);

//月份+1，天设置为0。下个月第0天，就是这个月最后一天
cld.add(Calendar.MONTH, 1);
cld.set(Calendar.DAY_OF_MONTH, 0); 
String lastDay = sdf.format(cld.getTime());
System.out.println("本月最后一天："+lastDay); //2019-09-30

cld.set(Calendar.DAY_OF_MONTH,1);
System.out.println("本月第一天："+ sdf.format(cld.getTime()) ); //2019-09-01



获取过去六个月包括本月

```java
  public static String[] getBaseMonths(String[] months, String yyyyMMdd)  {
        Calendar calendar = Calendar.getInstance();
        //DateFormat format = new SimpleDateFormat(YYYYMMDD);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(YYYYMM);
        try {
            if (ToolUtil.isNotEmpty(yyyyMMdd)) {
                date = format.parse(yyyyMMdd);
               // calendar.setTimeInMillis(date.getTime());
                calendar.setTime(date);
            } else {
                calendar.setTimeInMillis(System.currentTimeMillis());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println(calendar.get(Calendar.MONTH));
        //要先+1,才能把本月的算进去
        calendar.add(Calendar.MONTH,+1);
        //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
        for (int i = 0; i < months.length; i++) {
            //逐次往前推1个月
            //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            calendar.add(Calendar.MONTH,-1);
            //System.out.println(DateUtil.formatCurrentyyyyMM(calendar.getTime()));
            //System.out.println(calendar.get(Calendar.MONTH));
            months[months.length - 1 - i] = DateUtil.formatCurrentyyyyMM(calendar.getTime());//calendar.get(Calendar.YEAR) + "-" + fillZero(calendar.get(Calendar.MONTH));
        }
        return months;
    }
```





不在sql语句中如何返回distinct 

```java
List<String> userIds = userList.stream().map(OaUser::getUserId).distinct().collect(Collectors.toList());
```

```java
List<OaTask> records = page.getRecords();
List<OaTask> distinctResult = records.stream().distinct().collect(Collectors.toList());
List<OaQueryTaskResponse> oaQueryTaskResponses = EntityConvertUtils.convertAListToBList(distinctResult, OaQueryTaskResponse.class);
return page.setRecords(oaQueryTaskResponses);
```

```java
Math.toIntExact(inChargeTaskList.stream().filter(it -> it.getTaskStatus().equals(OaTaskStatusEnum.WAITING.getStatus())).count())
```

把long转int 超表示范围抛出异常



## 深拷贝问题

```java
 list.add(new OaWeeklyRequest.SenderUserInfo("CaiBoHan", "蓝犀技术-蔡博瀚（非洲疣猪）"));
        list.add(new OaWeeklyRequest.SenderUserInfo("XiongXueJiang", "蓝犀技术-熊学江（小浣熊）"));
        try {
            oaWeeklyRequest.setCopyer(deepCopy(list));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        list.clear();
        list.add(new OaWeeklyRequest.SenderUserInfo("XiongXueJiang", "蓝犀技术-熊学江（小浣熊）"));
        oaWeeklyRequest.setSender(list);
```

不采用deepCopy则一开始设置的list.clear copyer又变成空了

```java
 public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);

        List<T> copy_list = (List<T>) in.readObject();
        return copy_list;
    }
```

因为getInstance 不知道之前有没有被调用设置过其他日期没有清除。

要记得cal.setTime

```java
// 获得本周一0点时间
public static Date getThisWeekBegin() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date()); //先设置为当前时间
    cal.add(Calendar.DATE,-1);
    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return cal.getTime();
}
```
