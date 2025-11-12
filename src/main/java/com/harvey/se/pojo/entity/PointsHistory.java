package com.harvey.se.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.harvey.se.exception.BadRequestException;
import com.harvey.se.pojo.dto.PointsHistoryDto;
import com.harvey.se.pojo.enums.PointChangeReason;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`tb_points_history`")
@AllArgsConstructor
@NoArgsConstructor
public class PointsHistory {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("user_id")
    private Long userId;
    private PointChangeReason reason;
    private Integer flow;
    @TableField("create_time")
    private Date createDate;

    public static PointsHistory adapter(PointsHistoryDto dto) {
        if (dto == null) {
            throw new BadRequestException("请求参数不能是不存在的信息");

        }
        return new PointsHistory(
                dto.getId(),
                dto.getUserId(),
                dto.getReason(),
                dto.getFlow(),
                dto.getCreateDate()
        );
    }
}
