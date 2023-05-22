package com.gf.shardingjdbc;

import com.gf.shardingjdbc.entity.User;
import com.gf.shardingjdbc.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author GF
 * @since 2023/4/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RWTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void insertTest() {
        final User user = new User();
        user.setName("吕布");
        userMapper.insert(user);
    }

    //主从模式下，添加事务，查询走主库master
    @Transactional
    @Test
    public void selectTest() {
        User user2 = userMapper.selectById(1L);
        System.out.println(user2.toString());

        User user3 = userMapper.selectById(1L);
        System.out.println(user3.toString());
    }

    @Test
    public void selectAlgorithmTest() {
        userMapper.selectList(null);
        userMapper.selectList(null);
        userMapper.selectList(null);
        userMapper.selectList(null);
    }

}
