package com.ruoyi.framework.config;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.framework.interceptor.RepeatSubmitInterceptor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用配置
 *
 * @author ruoyi
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

  @Autowired
  private RepeatSubmitInterceptor repeatSubmitInterceptor;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    /** 本地文件上传路径 */
    registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + RuoYiConfig.getProfile() + "/");

    /** swagger配置 */
    registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  /**
   * 自定义拦截规则
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
  }
}