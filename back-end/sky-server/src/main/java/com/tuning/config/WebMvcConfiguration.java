package com.tuning.config;

import com.tuning.interceptor.JwtTokenAdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

  private final JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

  @Autowired
  public WebMvcConfiguration(JwtTokenAdminInterceptor jwtTokenAdminInterceptor) {
    this.jwtTokenAdminInterceptor = jwtTokenAdminInterceptor;
  }

  protected void addInterceptors(InterceptorRegistry registry) {
    System.out.println("开始注册自定义拦截器...");
    registry.addInterceptor(jwtTokenAdminInterceptor)
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/admin/employee/login");
    // 所有admin开头的，不管基层，都需要拦截，除了/admin/employee/login（登录）
  }
}
