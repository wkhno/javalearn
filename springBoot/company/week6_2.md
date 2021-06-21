# week6_2

写文件分页，需要创建父文件夹。

```java
public void createBillFile(List<String> companyIds, String startDate, String endDate) {
        if (ToolUtil.isEmpty(startDate) || ToolUtil.isEmpty(endDate)) {
            startDate = DateUtil.getYyyyMMdd(DateUtil.getYesterday(), false, true);
            endDate = DateUtil.getYyyyMMdd(DateUtil.getYesterday(), true, false);
        }
        //查询昨天对账单所有校验成功的数据
        for (String companyId : companyIds) {
            Long companyIdL = Long.valueOf(companyId);
            //第一步：设置输出的文件路径
            String localFilePath = StringUtil.getOrderNo() + ".csv";
            File writeFile = new File(Constant.openBillfile(localFilePath));
            File fileParent = writeFile.getParentFile();
            //判断文件路径是否存在
            if (!fileParent.exists()) {
                //不存在创建新的文件
                fileParent.mkdirs();
            }
            try {
                writeFile.createNewFile();
                //第二步：通过BufferedReader类创建一个使用默认大小输出缓冲区的缓冲字符输出流
                BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile));
                writeText.write("订单号,银行支付流水号,交易金额,收款人账号,收款人姓名,清算日期");
                LambdaQueryWrapper<BillItem> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.between(BillItem::getSynchronizationTime, startDate, endDate);
                queryWrapper.eq(BillItem::getCompanyId, companyIdL);
                queryWrapper.eq(BillItem::getItemStatus, BillItemStatusEnum.BILL_STATUS_SUCCESS.getStatus());
                int count = this.count(queryWrapper);
                int pageSize = 1000;
                int cycleNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
                //分页
                List<BillItem> billItems;
                for (int i = 0; i < cycleNum; i++) {
                    int startNum = i * pageSize;
                    String limitSql = "limit " + startNum + ",1000"; //最后一页可能不足1000条
                    queryWrapper.last(limitSql);
                    billItems = this.list(queryWrapper);
                    //第三步：将文档的下一行数据赋值给lineData，并判断是否为空，若不为空则输出
                    for (BillItem billItem : billItems) {
                        writeText.newLine();    //换行
                        //调用write的方法将字符串写到流中
                        StringBuilder sb = new StringBuilder();
                        sb.append("\t").append(billItem.getThirdOrderNo()).append(",");
                        sb.append("\t").append(billItem.getBankPayNo()).append(",");
                        sb.append(billItem.getAmount()).append(",");
                        sb.append("\t").append(billItem.getPayeeAccount()).append(",");
                        sb.append(billItem.getPayeeName()).append(",");
                        sb.append("\t").append(DateUtils.format(billItem.getSynchronizationTime(), DateUtils.YYYYMMDDHHMMSS));
                        writeText.write(sb.toString());
                    }
                    //使用缓冲区的刷新方法将数据刷到目的地中
                    writeText.flush();
                    //关闭缓冲区，缓冲区没有调用系统底层资源，真正调用底层资源的是FileWriter对象，缓冲区仅仅是一个提高效率的作用
                    //因此，此处的close()方法关闭的是被缓存的流对象
                    writeText.close();
                }
```



```sql
SELECT OrderId,DATE_SUB(OrderDate,INTERVAL 2 DAY) AS OrderPayDate
FROM Orders
时分秒部分不变日期减去两天
```



mybatis查询语句中的大于小于

&gt;

```
1.&lt; &gt;
2.或者<![CDATA[  <  ]]> 这是小于
3.直接>=应该也可以
3.不想用<where></where> 包裹但是想消除where开头的and 可以用where 1=1 
这样就可以直接使用<if></if> 标签
```

```sql
foreach collection="billItems" item="item" open="VALUES" close=";" separator=","
open close是固定值 不参与循环

统计本月数据
count(if(DATE_FORMAT(create_time, '%Y%m') = DATE_FORMAT(NOW(), '%Y%m'), 1, null)) AS thisMonthBillCount

IFNULL(sum(if(item_status = 2, amount, 0)),0) AS totalBillAmount
```





```java
表lxsysConfig:identification列
{"account":"浙江河马管家企业管理有限公司","bankCard":"15000098626625","bankName":"平安银行湖州分行营业部"}

注意key加上“”,JSON串格式  SQL：varchar JAVA entity:String 
Map<String,String> mp=JSONObject.parseObject(lxsysConfig.getIdentification,Map.class);
```

```java
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QsInvoiceClassEnum {
    /**
     * 普票
     */
    ORDINARY(0, "普票"),
    SPECIAL(1, "专票"),
    ;

    QsInvoiceClassEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @EnumValue
    private Integer code;
    
    private String name;
    
    public static QsInvoiceClassEnum getByCode(Integer code) {
        QsInvoiceClassEnum qsInvoiceClassEnum = SPECIAL;
        for (QsInvoiceClassEnum value : QsInvoiceClassEnum.values()) {
            if (NumberUtil.equals(code, value.getCode())) {
                qsInvoiceClassEnum = value;
            }
        }
        return qsInvoiceClassEnum;
    }
    加了@EnumValue 存数据库就是int
        java entity: 里面存Enum 
```





```
//serializeNulls 把Object转成json String 时null属性忽略，反序列化的时候null还在
//disaleHtmlEscaping()可以不转移<> 成\u之类
new GsonBuilder().setDateFormat(datePattern).disableHtmlEscaping().serializeNulls().create()
```
