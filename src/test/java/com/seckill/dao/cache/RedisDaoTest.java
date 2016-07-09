package com.seckill.dao.cache;

import com.seckill.dao.SeckillDao;
import com.seckill.model.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dello on 2016/7/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-applicationContext.xml"})
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillDao seckillDao;
    @Test
    public void testGetSeckill() throws Exception {
        Seckill seckill=seckillDao.queryById(1);
        String s = redisDao.putSeckill(seckill);
        System.out.println(s);
    }

    @Test
    public void testPutSeckill() throws Exception {
        Seckill seckill = redisDao.getSeckill(1);
        System.out.println(seckill);
    }
}
