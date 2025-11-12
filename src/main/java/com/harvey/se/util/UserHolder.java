package com.harvey.se.util;


import com.harvey.se.exception.UnauthorizedException;
import com.harvey.se.pojo.dto.UserDto;

/**
 * 将用户信息存在ThreadLocal,方便取用
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11 16:12
 */
public class UserHolder {
    private static final ThreadLocal<UserDto> TL = new ThreadLocal<>();

    public static void saveUser(UserDto user) {
        TL.set(user);
    }

    public static boolean existUser() {
        return TL.get() != null;
    }

    public static UserDto getUser() {
        UserDto userDto = TL.get();
        if (userDto == null) {
            throw new UnauthorizedException("need to login first");
        }
        return userDto;
    }

    public static void removeUser() {
        TL.remove();
    }

    public static Long currentUserId() {
        try {
            return getUser().getId();
        } catch (NullPointerException e) {
            throw new UnauthorizedException("未登录");
        }
    }
}
