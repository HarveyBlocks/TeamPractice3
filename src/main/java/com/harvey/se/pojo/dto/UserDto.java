package com.harvey.se.pojo.dto;

import com.harvey.se.exception.ResourceNotFountException;
import com.harvey.se.pojo.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户简要信息
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Data
@AllArgsConstructor
@ApiModel(description = "简单的用户信息")
public class UserDto implements Serializable {
    @ApiModelProperty(value = "用户权限", example = "0为root, 1为普通用户, 2为被拉入黑名单的用户, 3为vip(暂无)")
    private Integer role;
    @ApiModelProperty("用户主键")
    private Long id;
    @ApiModelProperty(value = "用户积分")
    private Integer points;
    @ApiModelProperty("昵称")
    private String nickname;

    public UserDto() {
    }

    public UserDto(User user) {
        if (user == null) {
            throw new ResourceNotFountException("请求不存在的资源");
        }
        this.role = user.getRole().getValue();
        this.id = user.getId();
        this.points = user.getPoints();
        this.nickname = user.getNickname();
    }
}
