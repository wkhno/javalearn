Spring常用

## 常用依赖

‘’‘xml

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.9.RELEASE</version>
    </dependency>
</dependencies>
```

## 注解说明

@Resource是先byName，不符合再继续byType。

@Autowired是先byType，如果唯一则注入，否则byName查找。而且必须要对象存在。

如果@Autowired自动装配的环境比较复杂，自动装配无法通过一个注解[@Autowired]完成的时候，我们可以使用@Qualifier(value="dog222") 可以显示指定id。

@Nullable 字段标记这个注解，说明这个字段可以为null.

@Component:加在类上，组件，说明这个类被Spring管理了，就是bean!