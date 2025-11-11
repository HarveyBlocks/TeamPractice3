package com.harvey.se.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.harvey.se.util.RedisConstants;
import lombok.Getter;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11 00:43
 */
@Getter
public enum PointChangeReason {
    CONSUME(0, "消费"), FEEDBACK(1, "反馈"), CHAT(2, "咨询"), IMPROVE_SELF_INFORMATION(3, "完善用户信息");
    @EnumValue
    private final Integer value;// 这里由于之前创建表的时候的限制, 以后还是用int
    private final String desc;// description

    PointChangeReason(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String toKeyPre() {
        switch (this) {
            case FEEDBACK:
                return RedisConstants.Point.FEEDBACK;
            case CHAT:
                return RedisConstants.Point.CHAT;
            case IMPROVE_SELF_INFORMATION:
                return RedisConstants.Point.CONSULTATION_CONTENT;
            case CONSUME:
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
