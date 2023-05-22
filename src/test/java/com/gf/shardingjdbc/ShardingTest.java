package com.gf.shardingjdbc;

import com.gf.shardingjdbc.entity.*;
import com.gf.shardingjdbc.mapper.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author GF
 * @since 2023/5/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private DictMapper dictMapper;

    @Resource
    private PersonMapper personMapper;

    @Test
    public void insertTest() {
        User user = new User();
        user.setName("afei");

        Order order = new Order();
        order.setUserId(1L);
        order.setOrderNo("00001");
        order.setAmount(BigDecimal.TEN);

        userMapper.insert(user);
        orderMapper.insert(order);
    }

    @Test
    public void testSelectFromOrderAndUser() {
        User user = userMapper.selectById(1L);
//        Order order = orderMapper.selectById(1L);
    }

    @Test
    public void testInsert() {
        Order order = new Order();
        order.setUserId(1L);
        order.setAmount(BigDecimal.TEN);
        for (int i = 0; i < 4; i++) {
            order.setId(null);
            order.setOrderNo("0000" + i);
            orderMapper.insert(order);
        }
    }


    @Test
    public void testSelect() {
        List<Order> orders = orderMapper.selectList(null);
    }

    @Test
    public void testB() {
        Order order = new Order();
        order.setUserId(1L);
        order.setAmount(BigDecimal.TEN);
        order.setId(null);
        order.setOrderNo("10000");
        orderMapper.insert(order);
        Long id = order.getId();
        String s = Long.toBinaryString(id);
        System.out.println(s);
    }


    @Test
    public void testId() throws InterruptedException {
        System.setProperty("worker-id", "10");
        System.setProperty("worker.id", "12");
        Order order = new Order();
        order.setUserId(1L);
        order.setAmount(BigDecimal.TEN);
        for (int i = 0; i < 10; i++) {
            order.setId(null);
            order.setOrderNo("10000");
            orderMapper.insert(order);
            Long id = order.getId();
            String s = Long.toBinaryString(id);
            System.out.println(id + "," + s);
            Thread.sleep(1);
        }
    }

    @Test
    public void testInsertMultiTable() {
        Order order = new Order();
        order.setUserId(1L);
        order.setAmount(BigDecimal.TEN);
        order.setOrderNo("10011");
        orderMapper.insert(order);
        for (int i = 0; i < 3; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(1L);
            orderItem.setOrderNo("10011");
            orderItem.setCount(i + 1);
            orderItem.setPrice(BigDecimal.TEN.multiply(new BigDecimal(i+1)));
            orderItemMapper.insert(orderItem);
        }
    }

    @Test
    public void testRelation() {
        List<OrderVo> orderAmount = orderMapper.getOrderAmount();
        System.out.println(orderAmount.size());
    }

    @Test
    public void testBroadcast() {
        Dict dict = new Dict();
        dict.setId(3L);
        dict.setDictType("AFEI");
        dictMapper.insert(dict);
    }

    @Test
    public void testBroadcastQuery() {
        dictMapper.selectById(1L);
        dictMapper.selectById(1L);
        dictMapper.selectById(1L);
        dictMapper.selectById(1L);
        dictMapper.selectById(1L);
    }

    @Test
    public void noSharding(){
        personMapper.selectById(1L);
    }
}
