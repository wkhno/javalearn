week7



手动获取spring管理的对象例如通过.class类名字符串获取

```java
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static  <T> Map<String, T> getBeans(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static String getValue(String key){
        Environment environment = getBean(Environment.class);
        return environment.getProperty(key);
    }
}
```



扔到stream之前也需要判断是否为NULL

```java
//原版没有==null
if (confList == null||ToolUtil.isEmpty(confList.stream().filter(one -> one.getUserId().equals(oaUserToDepartment.getUserId())).findFirst().orElse(null))) {
                        oaUserToDepartment.setIsLinkConf(0);
                    }
```

