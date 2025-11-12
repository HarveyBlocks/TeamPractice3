package com.harvey.se.util;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harvey.se.pojo.vo.DateRange;
import com.harvey.se.pojo.vo.IntRange;
import com.harvey.se.properties.ConstantsProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * 依据常量的初始化工具
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-20 18:19
 */
@Component
public class ConstantsInitializer {
    @Resource
    private ConstantsProperties constantsProperties;

    public <T> Page<T> initPage(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null || limit > Integer.parseInt(constantsProperties.getMaxPageSize())) {
            limit = Integer.valueOf(constantsProperties.getDefaultPageSize());
        }
        return new Page<>(page, limit);
    }


    public static DateRange initDateRange(String startDateString, String endDateString) throws ParseException {
        return new DateRange(initDate(startDateString), initDate(endDateString));
    }

    public static Date initDate(String dateString) throws ParseException {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        } else {
            return ServerConstants.DATE_TIME_FORMAT.parse(dateString);
        }
    }

    public static IntRange initIntRange(Integer lower, Integer upper) {
        return new IntRange(lower, upper);
    }

    public static Date nowDateTime() {
        return new Date(System.currentTimeMillis());
    }

}
