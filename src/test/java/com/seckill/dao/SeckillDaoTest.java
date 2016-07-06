package com.seckill.dao;

import com.seckill.model.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/** 配置Spring和Junit整合 junit启动时加载springIOC容器
 * Created by dello on 2016/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-applicationContext.xml")
public class SeckillDaoTest {

    private Logger logger= LoggerFactory.getLogger(SeckillDaoTest.class);
    //注入
    @Autowired
    private SeckillDao seckillDao;

//    03:47:01.213 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@71075444] will not be managed by Spring
//    03:47:01.262 [main] DEBUG c.s.dao.SeckillDao.reduceNumber - ==>  Preparing: UPDATE seckill SET number=number -1 WHERE number>0 and id=? and start_time <= ? and end_time > ?
//    03:47:01.404 [main] DEBUG c.s.dao.SeckillDao.reduceNumber - ==> Parameters: 1(Long), 2016-07-05 03:47:00.717(Timestamp), 2016-07-05 03:47:00.717(Timestamp)
//    03:47:01.519 [main] DEBUG c.s.dao.SeckillDao.reduceNumber - <==    Updates: 1
//    03:47:01.519 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@16eb3ea3]
//    03:47:01.520 [main] DEBUG o.s.jdbc.datasource.DataSourceUtils - Returning JDBC Connection to DataSource

    @Test
    public void testReduceNumber() throws Exception {
        int updateCount=seckillDao.reduceNumber(1,new Date());
        logger.info(String.valueOf(updateCount));
    }

    @Test
    public void testQueryById() throws Exception {
        long id=1;
        Seckill seckill=seckillDao.queryById(id);
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckills=seckillDao.queryAll(0,100);
        for(Seckill seckill:seckills){
            System.out.println(seckill);
        }
    }
}
