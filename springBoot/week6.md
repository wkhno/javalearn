[TOC]



### MyBatis接收List

```sql
GetQsSettlementAmountResponse getQsSettlementAmount(Date dateStart, Date dateEnd, @Param("companyIds") List<Integer> companyIds);

<foreach item="companyId" index="index" collection="companyIds" open="(" separator="," close=")">
                #{companyId}
</foreach>
```



### CloseableHttpClient pos方式

```java
/**

 * 发送HttpPost请求，参数为map
 * @param url
 * @param map
 * @return
   */
   public static String doPost(String url, Map<String, Object> map) {
   CloseableHttpClient httpclient = HttpClients.createDefault();
   List<NameValuePair> formparams = new ArrayList<NameValuePair>();
   for (Map.Entry<String, Object> entry : map.entrySet()) {
       //给参数赋值
       formparams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
   }
   UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
   HttpPost httppost = new HttpPost(url);
   httppost.setEntity(entity);
   CloseableHttpResponse response = null;
   try {
       response = httpclient.execute(httppost);
   } catch (IOException e) {
       e.printStackTrace();
   }
   HttpEntity entity1 = response.getEntity();
   String result = null;
   try {
       result = EntityUtils.toString(entity1);
   } catch (ParseException | IOException e) {
       e.printStackTrace();
   }
   return result;
   }
```

### 订单号生成

```java
 public static String getOrderNo() {
        String date = SIMPLE_DATE_FORMAT.format(LocalDateTime.now());
        return date + String.valueOf(new SnowflakeIdWorker().nextId()).substring(10);
    }

```

#### SQL表设计

![image-20210615153315984](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20210615153315984.png)

tinyint类型,longtext类型对应(String)  json对应Entity 

```java
@ApiModelProperty(value = "销售方信息")
@TableField(typeHandler = FastjsonTypeHandler.class)
private YnsSellerDTO seller;

@Data
public class YnsSellerDTO implements Serializable {
    private static final long serialVersionUID = 5652060249405470385L;

    @ApiModelProperty(value = "发票抬头")
    private String invoiceTitle;

    @ApiModelProperty(value = "税号")
    private String taxNumber;

    @ApiModelProperty(value = "地址、电话")
    private String addressPhone;

    @ApiModelProperty(value = "开户行及账号")
    private String bankAccount;
}


```



### 类似C++自定义Pair

```java
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pair<K,V> implements Serializable{
    private K first;
    
    private V second;
    
    public static <K,V> Pair<K,V> of(K first,V second){
        return new Pair<>(first,second);
    }
}
```



### @EnumValue注解的使用

创建枚举类，在需要存储数据库的属性上添加**`@EnumValue`**注解，在需要前端展示的属性上添加**`@JsonValue`**注解

@JsonValue
可以用在get方法或者属性字段上，一个类只能用一个，当加上@JsonValue注解时，该类的json化结果，只有这个get方法的返回值，而不是这个类的属性键值对.



### Map <String,Double> 的过滤

```java
Double rate = rateMap.entrySet().stream().filter((e) -> (e).getKey().equals(company.getId().toString())).collect(Collectors.toMap((e) -> (String) e.getKey(), (e) -> e.getValue())).get(company.getId().toString());
```

### double 转 String百分号

```java
 public static String getPercentFormat(double d,int IntegerDigits,int FractionDigits){
        NumberFormat nf = java.text.NumberFormat.getPercentInstance();
        nf.setMaximumIntegerDigits(IntegerDigits);//小数点前保留几位
        nf.setMinimumFractionDigits(FractionDigits);// 小数点后保留几位
        String str = nf.format(d);
        return str;
    }
```





```
userRolesList.stream().filter(it -> it.getCompanyId().equals(company.getId())).findFirst().orElse(new UserRoles());
orElse(null) 不用

companyMainstayService.getBaseMapper()
不一定要注入Mapper
```





```java
/**
 * BigDecimal小数转百分比
 *
 * @param b1
 * @return
 */
public static String percent(BigDecimal b1) {
    if (ToolUtil.isEmpty(b1)) {
        b1 = new BigDecimal("0");
    }
    b1 = b1.multiply(new BigDecimal("100"));
    BigDecimal noZeros = b1.stripTrailingZeros();
    String result = noZeros.toPlainString();
    return result + "%";
}
```





要用父类的属性来做equals和hashcode，不然会误判相等。用父类的id

```java
@EqualsAndHashCode(callSuper = true)
@Data
public class BalanceDetailResponse extends BalanceDetail {

    private String transTypeDesc;

    private String payTypeDesc;

    private String mainstayName;

}
mybatisplus eq应该是忽略大小写的
    utf8_bin 将字符串中的每一个字符用二进制数据存储，区分大小写。

    utf8_genera_ci 不区分大小写，ci为case insensitive的缩写，即大小写不敏感。

    utf8_general_cs 区分大小写，cs为case sensitive的缩写，即大小写敏感。

```



```sql
当company_ids是"，，"形式存在数据库中，他是变量不能用in，in只能接常量字符串
queryWrapper.last(" AND FIND_IN_SET(" + companyId + ", company_ids)  limit 1");
```



### saveOrUpdate save对应要saveBatch最后

```java
public void saveOrUpdate(List<CompanyMonthRebates> companyMonthRebatesList) {
        List<CompanyMonthRebates> newCompanyMonthRebatesList4Save = new ArrayList<>(companyMonthRebatesList.size());
        companyMonthRebatesList.forEach(companyMonthRebates -> {
            CompanyMonthRebates selectObj = getBaseMapper().selectOne(new LambdaQueryWrapper<CompanyMonthRebates>().eq(CompanyMonthRebates::getCompanyId, companyMonthRebates.getCompanyId())
                    .eq(CompanyMonthRebates::getMainstayId, companyMonthRebates.getMainstayId())
                    .last(" and DATE_FORMAT( date, \"%Y-%m\" ) =  \'" + DateUtils.getYyyymm(companyMonthRebates.getDate()) + "\' "));
            if (selectObj == null) {
                newCompanyMonthRebatesList4Save.add(companyMonthRebates);
            } else {
                companyMonthRebates.setId(selectObj.getId());
                this.updateById(companyMonthRebates);
            }
        });
        this.saveBatch(newCompanyMonthRebatesList4Save, 100);
    }
```

