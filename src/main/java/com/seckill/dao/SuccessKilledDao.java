package com.seckill.dao;

import com.seckill.model.SuccessKilled;

/**
 * Created by dello on 2016/7/3.
 */
public interface SuccessKilledDao {

    /**
     *  插入购买明细，可过滤重复
     * @param seckillId
     * @param userphone
     * @return 如果影响行数>1，标识更新的行数
     */
    int insertSuccessKilled(long seckillId,long userphone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckilId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(long seckilId);
}
