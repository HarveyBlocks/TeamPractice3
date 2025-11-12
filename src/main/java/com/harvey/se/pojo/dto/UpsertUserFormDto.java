package com.harvey.se.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录必要的信息
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "注册/更新用户信息需要的参数")
public class UpsertUserFormDto implements Serializable {
    @ApiModelProperty(value = "用户的电话号码,会在后端做正则的校验", required = true)
    private String phone;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty("昵称")
    private String nickname;
}

