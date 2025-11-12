package com.harvey.se.controller.admin;

import com.harvey.se.exception.BadRequestException;
import com.harvey.se.pojo.dto.PointsHistoryDto;
import com.harvey.se.pojo.dto.UserDto;
import com.harvey.se.pojo.dto.UserInfoDto;
import com.harvey.se.pojo.dto.UserInfoQuery;
import com.harvey.se.pojo.entity.User;
import com.harvey.se.pojo.vo.Null;
import com.harvey.se.pojo.vo.Result;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.service.PointService;
import com.harvey.se.service.UserService;
import com.harvey.se.util.ConstantsInitializer;
import com.harvey.se.util.RedisConstants;
import com.harvey.se.util.ServerConstants;
import com.harvey.se.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员管理用户信息
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Slf4j
@RestController
@Api(tags = "管理员管理用户信息")
@RequestMapping("/admin/user")
@EnableConfigurationProperties(ConstantsProperties.class)
public class AdminUserController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private ConstantsInitializer constantsInitializer;

    @GetMapping("/create")
    @ApiOperation(value = "测试用接口,生成虚假的User", notes = "生成100个虚假的用户,存入Redis")
    public Result<UserDto> createUser() {
        for (int i = 0; i < 1; i++) {
            Map<String, String> map = new HashMap<>();
            int token = i + 10000;
            System.out.println(token);
            String key = RedisConstants.User.USER_CACHE_KEY + token;
            map.put(RedisConstants.User.ID_FIELD, String.valueOf(token));
            map.put(RedisConstants.User.NICKNAME_FIELD, User.DEFAULT_NICKNAME);
            stringRedisTemplate.opsForHash().putAll(key, map);
        }
        return new Result<>(new UserDto(1, 1L, User.DEFAULT_POINTS, User.DEFAULT_NICKNAME));
    }

    /**
     * UserController 根据id查询用户Entity
     */
    @GetMapping("/one/{id}")
    @ApiOperation("根据id查询用户")
    public Result<UserInfoDto> queryUserInfoById(
            @PathVariable("id") @ApiParam(value = "目标用户的id", required = true) Long userId) {
        UserInfoDto userInfoDto = userService.queryUserInfoById(userId);
        return new Result<>(userInfoDto);
    }

    /**
     * UserController 根据id查询用户Entity
     */
    @GetMapping({"/all/{limit}/{page}", "/all/{limit}", "/all"})
    @ApiOperation("分页查询用户")
    public Result<List<UserInfoDto>> queryUserInfoByPage(
            @PathVariable(value = "limit", required = false) @ApiParam(value = "页号,从1开始", defaultValue = "1")
            Integer limit,
            @PathVariable(value = "page", required = false)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE) Integer page) {
        List<UserInfoDto> userInfoDto = userService.queryUserInfoByPage(constantsInitializer.initPage(page, limit));
        return new Result<>(userInfoDto);
    }

    /**
     * 复杂查询
     */
    @PostMapping({"/query"})
    @ApiOperation("复杂查询用户")
    public Result<List<UserInfoDto>> queryUserInfo(@RequestBody UserInfoQuery userInfoQuery) {
        return new Result<>(userService.queryUserInfo(userInfoQuery,
                constantsInitializer.initPage(userInfoQuery.getPage(), userInfoQuery.getLimit())
        ));
    }

    /**
     * UserController 根据id更新用户Entity
     */
    @PutMapping("/update")
    @ApiOperation("更新用户信息")
    public Result<Null> updateUserInfo(
            @RequestBody
            @ApiParam(value = "更新的用户实体, 字段为null时表示此字段不变, id是索引对应实体的依据", required = true)
            UserInfoDto newUser) {
        if (newUser == null || newUser.getId() == null) {
            throw new BadRequestException("except a new user in request body");
        }
        userService.updateUserInfo(newUser);
        return Result.ok();
    }

    @Resource
    private PointService pointService;

    @GetMapping({"/points/history/{user-id}/{limit}/{page}", "/points/history/{user-id}/{limit}",
            "/points/history/{user-id}"})
    @ApiOperation("查询某一用户的积分历史")
    public Result<List<PointsHistoryDto>> queryPointsHistory(
            @PathVariable(value = "user-id") @ApiParam(value = "目标用户的id", required = true) Long userId,
            @PathVariable(value = "limit", required = false)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE) Integer limit,
            @PathVariable(value = "page", required = false) @ApiParam(value = "页号", defaultValue = "1")
            Integer page) {
        return new Result<>(pointService.queryHistory(userId, constantsInitializer.initPage(page, limit)));
    }

}
