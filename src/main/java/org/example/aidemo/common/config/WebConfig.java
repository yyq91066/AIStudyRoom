package org.example.aidemo.common.config;

import org.example.aidemo.interceptor.LoginInterception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterception loginInterception;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //,"/api/ai/**","/api/location/**","/api/posts/**",,"/**"
        registry.addInterceptor(loginInterception).excludePathPatterns(
                "/api/user/login",//登录
                "/api/user/register",// 注册
                "/api/user/send-register-code",// 发送注册验证码
                //swagger
                "/doc.html",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**"
//                ,"/**"
        );//添加拦截器
    }
}
