SpringBoot默认支持的就是 slf4j + logback 的日志框架，所以也不用再多做啥设定，直接就可以用在 SpringBoot project上，log 系列注解最常用的就是 @Slf4j





jsp开头

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```





maven 依赖  JSP标签，JSTL标签、EL表达

```
      <!-- https://mvnrepository.com/artifact/javax.servlet.jsp.jstl/jstl-api -->
        <!--  JSTL表达式 依赖 -->
        <dependency>
            <groupId>javax.servlet.jsp.jstl</groupId>
            <artifactId>jstl-api</artifactId>
            <version>1.2</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/taglibs/standard -->
        <!-- Standard标签库 依赖 -->
        <dependency>
            <groupId>taglibs</groupId>
            <artifactId>standard</artifactId>
            <version>1.1.2</version>
        </dependency>
```



jsp 处理变量的作用域先后依次为：

```
page(默认) -> request -> session -> application
```

 scope 的作用域大小依次为：

```
application > session > request > page(默认)
```



## javaBean

实体类

必须要有一个无参构造

属性必须私有化

必须有对应的get/set方法

一般用来和数据库字段做映射ORM。

ORM（对象关系映射）

表--类

字段--属性

​	行记录--对象



c:choose

c:when 标签大坑

最后一个最好设置成c:otherwise

c:when score>60

c:when score<60

少枚举了等于的情况结果显示不出来