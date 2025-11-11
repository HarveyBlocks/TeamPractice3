package com.harvey.se.config;

import com.harvey.se.interceptor.AuthorizeInterceptor;
import com.harvey.se.interceptor.ExpireInterceptor;
import com.harvey.se.interceptor.Log2DbInterceptor;
import com.harvey.se.interceptor.LoginInterceptor;
import com.harvey.se.properties.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * MVC的配置类
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-01-03 14:12
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private AuthProperties authProperties;
    @Resource
    private ExpireInterceptor expireInterceptor;
    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private AuthorizeInterceptor authorizeInterceptor;

    @Resource
    private Log2DbInterceptor log2DbInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(expireInterceptor);
        registry.addInterceptor(log2DbInterceptor);

        List<String> excludePaths = authProperties.getExcludePaths();
        if (excludePaths == null) {
            excludePaths = Collections.emptyList();
        }
        List<String> includePaths = authProperties.getIncludePaths();
        if (includePaths == null) {
            includePaths = Collections.emptyList();
        }

        registry.addInterceptor(loginInterceptor).addPathPatterns(includePaths);

        registry.addInterceptor(authorizeInterceptor).addPathPatterns(includePaths);
    }


}
