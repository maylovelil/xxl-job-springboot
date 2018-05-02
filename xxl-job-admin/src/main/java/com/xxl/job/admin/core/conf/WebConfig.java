package com.xxl.job.admin.core.conf;

import com.xxl.job.admin.controller.interceptor.CookieInterceptor;
import com.xxl.job.admin.controller.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description:
 * @author: :MaYong
 * @Date: 2018/4/28 16:55
 */
@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration permissionAddInterceptor = registry.addInterceptor(getPermissionInterceptor());
        permissionAddInterceptor.addPathPatterns("/**");

        InterceptorRegistration cookieAddInterceptor = registry.addInterceptor(getCookieInterceptor());
        cookieAddInterceptor.addPathPatterns("/**");
    }

    @Bean
    public CookieInterceptor getCookieInterceptor() {
        return new CookieInterceptor();
    }

    @Bean
    public PermissionInterceptor getPermissionInterceptor(){
        return  new PermissionInterceptor();
    }
}
