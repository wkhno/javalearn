

指定要扫描的包，这个包下的注解自动生效

```xml
<context:component-scan base-package="com.example.pojo"/>
```

@Component有几个衍生注解，我们在web开发中，会按照mvc架构分三层。

dao @Repository

service @Service

controller@Controller

四个注解功能一样，都是将某个类注册到Spring中，装配Bean.

## 使用JavaConfig实现配置

javaConfig是Spring的一个子项目，Spring4之后成为1个核心功能。

用java类代替xml.

@Bean