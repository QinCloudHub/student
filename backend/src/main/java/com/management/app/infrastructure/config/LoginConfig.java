package com.management.app.infrastructure.config;

import com.management.app.support.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 将自定义登录拦截器注册到springMvc中
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    /**
     * 添加自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/login");
    }
}
