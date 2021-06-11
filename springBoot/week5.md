[TOC]

## week5

### @Transactional 注解参数详解

```java
@Target({ElementType.TYPE, ElementType.METHOD})：设定注解使用范围是类和方法上；
@Retention(RetentionPolicy.RUNTIME)：设定生命周期为全周期，支持使用反射机制的代码读取和使用；
@Inherited：指明如果该注解修饰的父类被子类继承，那么子类也具备事务特性；
@Documented：表明这个注解应该被 javadoc工具记录。
```

失效场景

- @Transactional 应用在非 public 修饰的方法上，不支持回滚；
- @Transactional 注解属性 propagation 设置错误；
- @Transactional 注解属性 rollbackFor 设置错误；
- 在同一个类中方法调用，导致 @Transactional 失效；
- 异常被 catch 处理了，导致 @Transactional 没办法回滚而失效；
- 数据库引擎不支持事务

### Redis序列化

序列化：把对象转换为字节序列的过程称为对象的序列化。
反序列化：把字节序列恢复为对象的过程称为对象的反序列化。

### Redis分布式锁条件

　管理后台的部署架构（多台tomcat服务器+redis【多台tomcat服务器访问一台redis】+mysql【多台tomcat服务器访问一台服务器上的mysql】）就满足使用分布式锁的条件。多台服务器要访问redis全局缓存的资源，如果不使用分布式锁就会出现问题。

  

使用mybatis的selectList方法，如果数据表中没有数据返回，则返回空集合[ ]，而不会返回null，这是mybatis作的封装

使用mybatis的selectOne方法，如果数据表中没有数据返回，则返回null

使用mybatis的selectOne方法，如果返回多条数据，则会抛出异常，而不是返回多条数据的第一条数据，看底层源码可知

- 数据库表字段如果有唯一约束，在mybatis中使用selectOne方法；
- 数据库表字段如果没有唯一约束，即使在业务逻辑上此字段时唯一的，但还是建议使用selectList方法。

- 如果数据库字段有唯一约束，那么你再添加相同的数据，则无法添加成功，但如果没有唯一约束，程序上可能出现并发，添加到数据库可以成功，但再次查询时会因为返回多条数据而导致程序出现问题。

### Optional类

这两个函数的区别：当user值不为null时，`orElse`函数依然会执行createUser()方法，而`orElseGet`函数并不会执行createUser()方法，大家可自行测试。

```java
@Test
public void test() {
    User user = null;
    user = Optional.ofNullable(user).orElse(createUser());
    user = Optional.ofNullable(user).orElseGet(() -> createUser());
    
}
public User createUser(){
    User user = new User();
    user.setName("zhangsan");
    return user;
}

Optional.ofNullable(user).ifPresent(u->{
    // TODO: do something
});


public static void main(String[] args) { 
    int a = 0; int b = 1; int c = 2; 
    ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
    bout.write(a); 
    bout.write(b);
    bout.write(c); 
    byte[] buff = bout.toByteArray(); 
    for (int i = 0; i < buff.length; i++) 
        System.out.println(buff[i]); 
    System.out.println("***********************"); 
    ByteArrayInputStream bin = new ByteArrayInputStream(buff); 
    while ((b = bin.read()) != -1) { 
        System.out.println(b); 
    } 
}
/** * 读取文件内容 * * @param filename 文件名 * @return */
public String read(String filename) throws Exception { 
    FileInputStream fis = new.FileInputStream(filename); 
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024]; 
    int len = 0; // 将内容读到buffer中，读到末尾为-1 
    while ((len = fis.read(buffer)) != -1) { // 本例子将每次读到字节数组(buffer变量)内容写到内存缓冲区中，起到保存每次内容的作用 
        outStream.write(buffer, 0, len); 
    } 
    byte[] data = outStream.toByteArray(); // 取内存中保存的数据 
    fis.close(); 
    String result = new String(data, "UTF-8"); 
    return result; 
}

/** * 将输入流转为字节数组 * @author Jason * @date 9:49 AM 6/19/2020 * @param * @return */ 	public static byte[] toByteArray(InputStream in) throws IOException { 
    ByteOutputStream byteOutputStream = new ByteOutputStream(); 
    byte[] bytes = new byte[1024]; 
    int len=0; 
    while ((len=in.read(bytes))!=-1){ 
        byteOutputStream.write(bytes,0,len); 
    } 
    return byteOutputStream.toByteArray(); 
}

```



### @RequestBody

- json字符串中，如果value为""的话，后端对应属性如果是String类型的，那么接受到的就是""，如果是后端属性的类型是Integer、Double等类型，那么接收到的就是null。
- json字符串中，如果value为null的话，后端对应收到的就是null。



excel导入系统 要区分版本xls和xlsx

```java
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = "";
        try {
            fileType = fileName.substring(fileName.lastIndexOf("."));
        }catch (Exception e){
            throw new BusinessException(BizError.FILE_TYPE_ERROR);
        }
        if (EXCEL2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);
            //2003-
        } else if (EXCEL2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);
            //2007+
        } else {
            throw new BusinessException(BizError.FILE_TYPE_ERROR);
        }
        return wb;
    }
```

java Boolean也可以对应SQL bit



MyBatisPlus加快updateBatch和saveBatch时间

最后发现在sql链接后追加: `rewriteBatchedStatements=true`. 再次尝试, 已经缩减到`100`ms!



导入表格最粗暴的并发

函数加synchronized

加@Transactional注解

```java
@Transactional(rollbackFor = Exception.class)
    public synchronized BaseResponse<ImportCompanyIndustryResponse> importCompanyIndustry(
            MultipartFile file, HttpServletRequest request
    ) throws Exception {
```



### 异步调用

@EnableAsync
在启动类或者Control类加上 @EnableAsync 注解

@EnableAsync注解的意思是可以异步执行，就是开启多线程的意思。可以标注在方法、类上。@Async所修饰的函数不要定义为static类型，这样异步调用不会生效



@Async 失效的原因

***1.主启动类没有加@EnableAsync注解***

***2.异步方法不要和调用异步方法的方法在同一个类中***



java 反射 返回包名加类名

```
this.getClass().getName() 
```



### redis分布式锁

```java
/**
 * 最终加强分布式锁
 *
 * @return 是否获取到
 */

public boolean lock(String key) {
    String lock = key;
    // 利用lambda表达式
    try {

        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {

            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());


            if (acquire) {
                return true;
            } else {

                byte[] value = connection.get(lock.getBytes());

                if (Objects.nonNull(value) && value.length > 0) {

                    long expireTime = Long.parseLong(new String(value));

                    if (expireTime < System.currentTimeMillis()) {
                        // 如果锁已经过期
                        byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                        // 防止死锁
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    } catch (RedisConnectionFailureException e) {
        return useMysql(key, 0, LOCK_EXPIRE,e);
    } catch (QueryTimeoutException queryTimeoutException){
        return useMysql(key, 0, LOCK_EXPIRE,queryTimeoutException);
    }
}
```
