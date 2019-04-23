package com.haoche.yltms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] path = {"/unverify/**","/images/**","/route/**","/fonts/**","/webfonts/**","/admLogin","/js/**","/","/login","/signUp", "/user/saveUser","/img/**","/uploaded/**", "/jquery/**", "/layui/**", "/css/**"};
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(path);
    }

}
