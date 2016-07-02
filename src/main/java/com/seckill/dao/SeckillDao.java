package com.seckill.dao;

import com.seckill.model.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by dello on 2016/7/3.
 */
public interface SeckillDao {
    /**
     *  减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(long seckillId, Date killTime);

    /**
     *根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀列表
     * @param offet
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offet,int limit);
}
