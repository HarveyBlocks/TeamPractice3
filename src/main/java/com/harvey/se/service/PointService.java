package com.harvey.se.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.harvey.se.pojo.dto.PointsHistoryDto;
import com.harvey.se.pojo.dto.UserDto;
import com.harvey.se.pojo.entity.PointsHistory;
import com.harvey.se.pojo.enums.PointChangeReason;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 积分接口, 本身与SQL无关
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
public interface PointService extends IService<PointsHistory> {

    /**
     * @param reason  键
     * @param user    目标用户
     * @param count   一次间隔允许加几次分
     * @param point   每次加分
     * @param timeout 时间间隔
     * @param unit    间隔单位
     */
    void add(PointChangeReason reason, UserDto user, int count, int point, int timeout, TimeUnit unit);

    void record(PointsHistoryDto pointsHistoryDto);

    List<PointsHistoryDto> queryHistory(Long userId, Page<PointsHistory> page);
}
