package com.harvey.se.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.harvey.se.pojo.dto.*;
import com.harvey.se.pojo.entity.User;
import com.harvey.se.pojo.enums.PointChangeReason;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户业务的实现
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
public interface UserService extends IService<User> {
    /**
     * 生成校验码并发送
     *
     * @param phone 手机号
     * @return 校验码
     */
    String sendCode(String phone);

    User selectByPhone(String phone);

    /**
     * 用验证码登录验证
     *
     * @param codeCache 会话保存的验证码
     * @param phone     新请求的手机号
     * @param code      新请求的验证码
     * @return 用户信息
     */
    User loginByCode(String codeCache, String phone, String code);

    /**
     * 校验用户名密码
     *
     * @param phone    电话号码, 也做账号
     * @param password 密码
     * @return 若返回id为-1的则为不存在用户,<br>
     * 若返回null则为用户名密码错误,<br>
     * 否则返回正确查到的用户<br>
     */
    User loginByPassword(String phone, String password);


    String chooseLoginWay(LoginFormDto loginForm);

    String register(UpsertUserFormDto registerForm);

    @Transactional
    String updateUser(UpsertUserFormDto userDTO, String token);

    UserDto queryUserByIdWithRedisson(Long userId) throws InterruptedException;

    UserDto queryUserById(Long userId);

    UserInfoDto queryUserInfoById(Long userId);

    List<UserInfoDto> queryUserInfoByPage(Page<User> page);

    void updateUserInfo(UserInfoDto newUser);

    List<User> queryUserByPage(Page<User> page);

    void loadCache(Long id) throws InterruptedException;


    void increasePoint(Long userId, PointChangeReason reason, int currentPoint, int flow);

    List<UserInfoDto> queryUserInfo(UserInfoQuery userInfoQuery, Page<User> page);
}
