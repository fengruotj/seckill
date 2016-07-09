package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillExcetpion;
import com.seckill.model.Seckill;

import java.util.List;

/**
 * Created by dello on 2016/7/5.
 * 业务接口站在“使用者”的角度实现接口
 * 三个方面：方法定义粒度，参数，返回类型（return/异常）
 */
public interface SeckillService {
    /**
     *  查询所有秒杀记录
     * @return
     */
    public List<Seckill> getSeckillList();

    /**
     *  查询秒杀记录通过id
     * @param id
     * @return
     */
    public Seckill getSeckillById(long id);

    /**
     *  秒杀开启输出秒杀地址，
     *  否则输出系统时间和秒杀时间
     *  @param seckillId
     */
    public Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillExcetpion,SeckillCloseException,RepeatKillException;

    /**
     * 执行存储过程秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}
