package org.example.points.config;

import org.example.points.filter.LoginUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * <h1>Web Mvc 配置</h1>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginUserInfoInterceptor loginUserInfoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加用户身份统一登录拦截的拦截器
        registry.addInterceptor(loginUserInfoInterceptor);
//                .excludePathPatterns("/article/portal", "/article/portal/**").order(0);
    }
}
