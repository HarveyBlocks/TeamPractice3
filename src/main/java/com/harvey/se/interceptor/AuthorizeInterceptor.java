package com.harvey.se.interceptor;

import com.harvey.se.pojo.enums.UserRole;
import com.harvey.se.util.ServerConstants;
import com.harvey.se.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权拦截器
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Slf4j
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {
        int roleValue = UserHolder.getUser().getRole();
        if (roleValue == UserRole.BLOCKED.getValue()) {
            // 被拉入黑名单的, 直接无权访问
            response.setStatus(401);
            return false;
        }
        if (ServerConstants.ROOT_AUTH_URI.contains(request.getRequestURI())) {
            if (roleValue != UserRole.ROOT.getValue()) {
                log.info("root coming");
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }


}