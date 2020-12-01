# SpringBoot自动装配原理

### SpringBoot 注解

@SpringBootConfiguration:springboot的配置

​	@Configuration spring配置类

​    @Component   说明这也是spring的组件

@EnableAutoConfiguration   自动配置

​		@AutoConfigurationPackage  自动配置包

​					@Import(AutoConfigurationPackages.Registrar.class) :导入选择器包注册

​		@Import(AutoConfigurationImportSelector.class):



springboot所有自动配置都是在启动的时候扫描并加载：spring.factories 所有的自动配置类都在这里面，但是不一定生效。要判断条件是否成立，只要导入了对应的start，就有对应的启动器了，有了启动器，我们自动装配就会生效，然后就配置成功。



**yaml文件中大写就会报错**

yaml 中日期格式 birth: 2019/11/02

以及名称和属性之间一定要有一个空格

yaml 中的属性初始化可以用@Value注解在Set方法上，效果等价。

yaml与yml一样

Properties文件来配置属性 就不需要@ConfigurationProperties注解，改用PropertySource

注意classpath:后面不能有空格 否则路径找不到

```java
@PropertySource(value = "classpath:yyp.properties")
@Value("${name}")
```

​                     

JSR303校验找不到时导入依赖

```java
		<dependency>
			<groupId>org.hibernate.validator</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>6.0.0.Final</version>
		</dependency>
```

