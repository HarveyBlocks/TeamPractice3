package com.harvey.se.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.harvey.se.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户映射
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
