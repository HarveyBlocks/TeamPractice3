package com.harvey.se.interceptor;

import com.harvey.se.service.UserActionLogService;
import com.harvey.se.util.UserHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private UserActionLogService userActionLogService;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!UserHolder.existUser()) {
            response.setStatus(401);
            return false;
        }
        return true;
    }


}