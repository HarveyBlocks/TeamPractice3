package com.harvey.se.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harvey.se.dao.PointsHistoryMapper;
import com.harvey.se.exception.UncompletedException;
import com.harvey.se.pojo.dto.PointsHistoryDto;
import com.harvey.se.pojo.dto.UserDto;
import com.harvey.se.pojo.entity.PointsHistory;
import com.harvey.se.pojo.enums.PointChangeReason;
import com.harvey.se.service.PointService;
import com.harvey.se.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 对积分的一些操作
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Slf4j
@Service
public class PointServiceImpl extends ServiceImpl<PointsHistoryMapper, PointsHistory> implements PointService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserService userService;

    @Override
    public void add(PointChangeReason reason, UserDto user, int count, int point, int timeout, TimeUnit unit) {
        // 1. 检查缓存, 是否已经加过分
        String flagKey = reason.toKeyPre() + user.getId();
        String release = stringRedisTemplate.opsForValue().get(flagKey);
        int releaseCnt;
        if (release != null && !release.isEmpty()) {
            releaseCnt = Integer.parseInt(release);
            if (releaseCnt == 0) {
                // 不能执行
                return;
            }
            releaseCnt--;
        } else {
            releaseCnt = count - 1;
        }
        // 2. 增加/修改缓存标记
        stringRedisTemplate.opsForValue().set(flagKey, String.valueOf(releaseCnt), timeout, unit);
        // 3. 增加积分
        userService.increasePoint(user.getId(), reason, user.getPoints(), point);
    }

    @Override
    public void record(PointsHistoryDto pointsHistoryDto) {
        boolean save = super.save(PointsHistory.adapter(pointsHistoryDto));
        if (!save) {
            log.warn("保存积分历史失败");
        }
    }

    @Override
    public List<PointsHistoryDto> queryHistory(Long userId, Page<PointsHistory> page) {
        return new LambdaQueryChainWrapper<>(baseMapper)
                .eq(PointsHistory::getUserId, userId)
                .page(page)
                .getRecords()
                .stream()
                .map(PointsHistoryDto::adapter)
                .collect(Collectors.toList());
    }
}
