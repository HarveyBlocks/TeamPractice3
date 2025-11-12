package com.harvey.se.pojo.dto;

import com.harvey.se.exception.BadRequestException;
import com.harvey.se.pojo.entity.PointsHistory;
import com.harvey.se.pojo.enums.PointChangeReason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "积分获取历史")
public class PointsHistoryDto {

    @ApiModelProperty("积分获取历史")
    private Long id;

    @ApiModelProperty("积分")
    private Long userId;
    @ApiModelProperty("积分变化原因")
    private PointChangeReason reason;
    @ApiModelProperty("积分变化数")
    private Integer flow;
    @ApiModelProperty("积分变化发生的时间")
    private Date createDate;
    public static PointsHistoryDto adapter(PointsHistory entity) {
        if (entity == null) {
            throw new BadRequestException("请求参数不能是不存在的信息");

        }
        return new PointsHistoryDto(
                entity.getId(),
                entity.getUserId(),
                entity.getReason(),
                entity.getFlow(),
                entity.getCreateDate()
        );
    }
}
