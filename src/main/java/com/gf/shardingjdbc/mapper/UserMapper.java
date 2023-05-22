package com.gf.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gf.shardingjdbc.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author GF
 * @since 2023/3/31
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
