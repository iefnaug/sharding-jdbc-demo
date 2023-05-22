package com.gf.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gf.shardingjdbc.entity.Order;
import com.gf.shardingjdbc.entity.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author GF
 * @since 2023/4/1
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select({
            "SELECT o.order_no, SUM(oi.price * oi.count) amount",
            "FROM t_order o JOIN t_order_item oi on o.order_no = oi.order_no",
            "GROUP BY o.order_no;"
    })
    List<OrderVo> getOrderAmount();


    IPage<Order> queryOrders(IPage<?> page, LambdaQueryWrapper<Order> queryWrapper);

}
