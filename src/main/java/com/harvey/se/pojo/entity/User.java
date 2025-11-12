package com.harvey.se.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.harvey.se.pojo.dto.UserDto;
import com.harvey.se.pojo.dto.UserInfoDto;
import com.harvey.se.pojo.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`tb_user`")
@AllArgsConstructor
public class User implements Serializable {

    @TableField(exist = false)
    public static final String DEFAULT_NICKNAME = "unknown";
    public static final int DEFAULT_POINTS = 0;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 密码，加密存储
     */
    private String password;

    /**
     * 昵称，默认是随机字符
     */
    private String nickname;
    private Integer points;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 角色,权限
     */
    private UserRole role;


    public User() {
    }

    public User(UserDto userDTO) {
        this.nickname = userDTO.getNickname();
        this.id = userDTO.getId();
        this.points = userDTO.getPoints();
        this.role = UserRole.create(userDTO.getRole());
        this.updateTime = LocalDateTime.now();
    }

    public User(UserInfoDto userInfoDto) {
        this(
                userInfoDto.getId(), userInfoDto.getPhone(), null, userInfoDto.getNickname(),
                userInfoDto.getPoints(), null, null, userInfoDto.getRole()
        );
    }
}
