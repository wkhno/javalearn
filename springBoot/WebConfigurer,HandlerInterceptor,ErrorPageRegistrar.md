```java
registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/index.html");
```

写在配置类，implements WebMvcConfigurer,这个说明拦截除了index.html以外的所有请求。



进一步Interceptor implements HandlerInterceptor

```java
@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
        String contextPath=session.getServletContext().getContextPath();
        String []requireAuthPages=new String[]{"index",};
        String uri=request.getRequestURI();
        uri=StringUtils.remove(uri,contextPath+"/");
        String page=uri;
        if(beginingWith(page,requireAuthPages)){
            User user=(User)session.getAttribute("user");
            if(user==null){
                response.sendRedirect("login");
                return false;
            }
        }
        return true;
    }
```

只有除了index.html以外，虽然所有被拦截，但是只有配置在requireAuthPages里面的以那些字符串开始的请求路径会被重定向到login。如“index123","indextt",注意这后面不能加//之类，会多次重定向。



因为index//会被转到login，login变成index.html，但是后面又有/，会被再次经过configurer，(被认为是index开头而不是index.html)再次重定向到login.

```java
@Component
public class ErrorConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage errorPage=new ErrorPage(HttpStatus.NOT_FOUND,"/index.html");
        registry.addErrorPages(errorPage);
    }
}
```

