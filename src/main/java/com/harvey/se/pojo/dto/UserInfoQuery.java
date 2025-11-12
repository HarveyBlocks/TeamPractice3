package com.harvey.se.pojo.dto;

import com.harvey.se.pojo.enums.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 查询请求参数
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "查询用户请求参数, 如果不查询请使用null, 不建议使用空字符串")
public class UserInfoQuery {
    @ApiModelProperty(value = "昵称, 模糊查询")
    private String nickname;
    @ApiModelProperty(value = "电话号码, 模糊查询, null表示全查")
    private String phone;
    @ApiModelProperty(value = "权限, null表示全查")
    private UserRole role;
    @ApiParam(value = "页号,从1开始", defaultValue = "1")
    private Integer page;
    @ApiModelProperty(value = "页码长")
    private Integer limit;
}
