## 狂神springboot 员工管理项目

### 首页配置

注意点，所有页面的静态资源都需要使用thymeleaf接管。

url @{}

### 页面国际化

#### 国际化 中英文切换 i18n

```
login_zh_CN.properties
login_en_US.properties
login.properties
```

idea 中可以切换至resource Bundle可视化配置

login.properties 默认

url #{}

在thymeleaf中引用要以#{}开头

#### 国际化必须要写一个implements LocaleResolver的类

#### 要在mymvconfig中注入.此处@bean 方法名不要与返回参数名字一样

```java
    @Bean/@Component
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
```



thymeleaf 少用 多看看springboot+vue+axios 前后端分离的。

真实前端页面设计往往用vue 不用thymeleaf