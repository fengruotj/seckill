package com.seckill.dao;

import com.seckill.model.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

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
    List<Seckill> queryAll(@Param("offet") int offet,@Param("limit") int limit);

    /**
     * 使用存储过程进行秒杀操作
     * @param map
     */
    void killByProdcedure(Map<String,Object> map);
}
