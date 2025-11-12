package com.harvey.se.pojo.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 角色(用于授权)
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Getter
public enum UserRole {
    ROOT(0, "管理员"), NORMAL(1, "普通"), BLOCKED(2, "被拉入黑名单的");

    @EnumValue
    private final Integer value;// 这里由于之前创建表的时候的限制, 以后还是用int
    private final String desc;// description


    UserRole(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static UserRole create(Integer roleValue) {
        if (roleValue == null) {
            return UserRole.NORMAL;
        }
        UserRole role;
        switch (roleValue) {
            case 0:
                role = ROOT;
                break;
            case 1:
            default:
                role = NORMAL;
        }
        return role;
    }


}
