# spring基础



## 依赖注入

### 扩展方式注入

p命名和c命名空间不能直接使用，需要导入xml约束。

`xmlns:p="http://www.springframework.org/schema/p"`

`xmlns:c="http://www.springframework.org/schema/c"`

## 自动装配

autowired



byname:会自动在容器上下文中查找，和自己对象set方法后面值对应的beanid.

<bean id="people" class="com.kuang.pojo.People" autowire="byName">

</bean>

byType:会自动在容器上下文中查找，和自己对象属性类型相同的bean.根据的是bean后面的class

**小结：**

- byname的时候，需要保证所有bean的id唯一，并且这个bean需要和自动注入的属性的set方法值一致。
- bytype的时候，需要保证所有bean的class唯一，并且这个bean需要和自动注入的bean的class一致。

## 使用注解实现自动装配

jdk1.5,spring2.5支持注解开发。

要配置xml

`xmlns:context="http://www.springframework.org/schema/context"`

```
http://www.springframework.org/schema/context
https://www.springframework.org/schema/context/spring-context.xsd
```

`<context:annotation-config/>`

@Autowired

使用Autowired就可以不用编写set方法了，前提是你这个自动装配的属性在IOC（Spring）容器中存在，且byType。

@Nullable 字段标记这个注解，说明这个字段可以为null.

如果@Autowired自动装配的环境比较复杂，自动装配无法通过一个注解[@Autowired]完成的时候，我们可以使用@Qualifier(value="dog222") 可以显示指定id。

非Spring专用注解

@Resource(name="dog222")

@Resource是先byName，不符合再继续byType。

@Autowired是先byType，如果唯一则注入，否则byName查找。而且必须要对象存在。