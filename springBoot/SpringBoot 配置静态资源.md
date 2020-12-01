SpringBoot 配置静态资源

1.在springBoot，我们可以使用以下方式处理静态资源

- webjars  localhost:8800/webjars/

- public,static ./** , resources   localhost:8800/

  

2.优先级： resources>static(默认)>public



使用webjars 百度webjars maven 导入类似依赖

```java
<dependency>
   <groupId>org.webjars</groupId>
   <artifactId>jquery</artifactId>
   <version>3.4.1</version>
</dependency>
```