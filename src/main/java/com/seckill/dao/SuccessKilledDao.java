package com.seckill.dao;

import com.seckill.model.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by dello on 2016/7/3.
 */
public interface SuccessKilledDao {

    /**
     *  插入购买明细，可过滤重复
     * @param seckillId
     * @param userphone
     * @return 如果影响行数大于1，标识更新的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userphone") long userphone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckilId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckilId") long seckilId,@Param("userphone") long userphone);
}
