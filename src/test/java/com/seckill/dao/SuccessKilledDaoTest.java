package com.seckill.dao;

import com.seckill.model.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 配置Spring和Junit整合 junit启动时加载springIOC容器
 * Created by dello on 2016/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-applicationContext.xml")
public class SuccessKilledDaoTest {
    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        int insertCount=successKilledDao.insertSuccessKilled(2,13072783);
        System.out.println(insertCount);
    }

//    04:00:45.887 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@5965d37] will not be managed by Spring
//    04:00:45.951 [main] DEBUG c.s.d.S.queryByIdWithSeckill - ==>  Preparing: SELECT sk.user_phone,sk.state,sk.create_time, s.id "seckill.id", s.name "seckill.name", s.number "seckill.number", s.create_time "seckill.create_time", s.end_time "seckill.end_time", s.start_time "seckill.start_time" FROM success_killed sk INNER JOIN seckill s on sk.seckill_id=s.id WHERE sk.seckill_id=? AND sk.user_phone=?
//    04:00:46.063 [main] DEBUG c.s.d.S.queryByIdWithSeckill - ==> Parameters: 1(Long), 13072783(Long)
//    04:00:46.180 [main] DEBUG c.s.d.S.queryByIdWithSeckill - <==      Total: 1
//    04:00:46.187 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5524cca1]
//    04:00:46.187 [main] DEBUG o.s.jdbc.datasource.DataSourceUtils - Returning JDBC Connection to DataSource
    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1, 13072783);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}
