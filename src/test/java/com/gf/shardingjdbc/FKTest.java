package com.gf.shardingjdbc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gf.shardingjdbc.entity.*;
import com.gf.shardingjdbc.mapper.DictMapper;
import com.gf.shardingjdbc.mapper.OrderItemMapper;
import com.gf.shardingjdbc.mapper.OrderMapper;
import com.gf.shardingjdbc.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author GF
 * @since 2023/4/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FKTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private DictMapper dictMapper;

    @Test
    public void insertTest() {
        final User user = new User();
        user.setName("分库测试");
        userMapper.insert(user);

        final Order order = new Order();
        order.setOrderNo("aaabbcc");
        order.setAmount(new BigDecimal("1.23"));
        order.setUserId(1L);
        orderMapper.insert(order);
    }


    @Test
    public void insertTest2() {
        for (int i = 1; i < 5; i++) {
            final Order order = new Order();
            order.setOrderNo("test" + i);
            order.setAmount(new BigDecimal("1.23"));
            order.setUserId(1L);
            orderMapper.insert(order);
        }

        for (int i = 5; i < 9; i++) {
            final Order order = new Order();
            order.setOrderNo("test" + i);
            order.setAmount(new BigDecimal("1.25"));
            order.setUserId(2L);
            orderMapper.insert(order);
        }
    }

    @Test
    public void selectAllTest() {
        final List<Order> orders = orderMapper.selectList(null);
        System.out.println(orders.size());
    }

    @Test
    public void selectTest() {
        final Order order = orderMapper.selectOne(Wrappers.<Order>lambdaQuery().eq(Order::getOrderNo, "test2"));
        System.out.println(order);
    }


    @Test
    public void insertTest3() {
        for (int i = 1; i < 5; i++) {
            final Order order = new Order();
            order.setOrderNo("test" + i);
            order.setAmount(new BigDecimal("1.23"));
            order.setUserId(1L);
//            orderMapper.insert(order);
            for (int j = 0; j < 3; j++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo("test" + i);
                orderItem.setCount(2);
                orderItem.setPrice(new BigDecimal("2.0"));
                orderItem.setUserId(1L);
                orderItemMapper.insert(orderItem);
            }
        }

        for (int i = 5; i < 9; i++) {
            final Order order = new Order();
            order.setOrderNo("test" + i);
            order.setAmount(new BigDecimal("1.25"));
            order.setUserId(2L);
//            orderMapper.insert(order);
            for (int j = 0; j < 3; j++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo("test" + i);
                orderItem.setCount(3);
                orderItem.setPrice(new BigDecimal("1.0"));
                orderItem.setUserId(2L);
                orderItemMapper.insert(orderItem);
            }
        }
    }


    @Test
    public void selectTest3() {
        final List<OrderVo> list = orderMapper.getOrderAmount();
        list.forEach(System.out::println);
    }

    @Test
    public void broadcastInsertTest() {
        Dict dict = new Dict();
        dict.setDictType("NODE");

        dictMapper.insert(dict);
    }

    @Test
    public void selectTest04() {
        final List<Dict> dicts = dictMapper.selectList(null);
        dicts.forEach(System.out::println);
    }

    @Test
    public void pageTest() {
        final Page<Order> page = new Page<>();
        page.setCurrent(2);
        page.setSize(4);
        final IPage<Order> orderIPage = orderMapper.selectPage(page, Wrappers.<Order>lambdaQuery().orderByAsc(Order::getId));
        final List<Order> records = orderIPage.getRecords();
        records.forEach(System.out::println);
    }
}
