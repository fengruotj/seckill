package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dao.cache.RedisDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillExcetpion;
import com.seckill.model.Seckill;
import com.seckill.model.SuccessKilled;
import com.seckill.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dello on 2016/7/5.
 */

@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger= LoggerFactory.getLogger(SeckillServiceImpl.class);

    //md5盐值字符串，用来混淆MD5
    private final String slat="fasfewewrwre12345489";
    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private RedisDao redisDao;

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getSeckillById(long id) {
        return seckillDao.queryById(id);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //使用Redis缓存  建立在超时的基础上
        /**
         *  get from Cache
         *  if(null)
         *      get db;
         *   else
         *    put Cache;
         *
         *    1.访问Redis
         */

        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill==null){
           // 2.访问数据库
            seckill = seckillDao.queryById(seckillId);
            if(seckill==null){
                return new Exposer(false,seckillId);
            }else {
                //放入Redis缓存
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5=getMd5(seckillId);//TODO
        return new Exposer(true,seckillId,md5);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    /**
     * 使用注解控制事务的优点
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络HTTP请求或者剥离到事务方法外面
     * 3.不是所有的方法都需要事务，如只有一条修改操作，或者说是只读操作不需要事务控制
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillExcetpion, SeckillCloseException, RepeatKillException {
        if(md5==null|| !getMd5(seckillId).equals(md5)){
            throw new SeckillExcetpion("seckill Data overwrite");
        }

        try {
            Seckill seckill=seckillDao.queryById(seckillId);
            Date nowTime=new Date();
            //插入秒杀记率u
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if(insertCount<=0){
                throw new RepeatKillException("seckill repeat");
            }else{
                //减库存
                int updateCount=seckillDao.reduceNumber(seckillId,nowTime);
                if(updateCount<=0){
                    //没有更新操作,秒杀结束
                    throw new SeckillCloseException("seckill is closed");
                }else{
                    //秒杀成功
                    SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }
        catch (RepeatKillException e2){
            throw e2;
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译器异常转化为运行期异常
            throw new SeckillExcetpion("seckill innor error:"+ e.getMessage());
        }
    }

    //Seckiill 存储过程调用
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        if(md5==null||!getMd5(seckillId).equals(md5)){
            return new SeckillExecution(seckillId,SeckillStateEnum.DATA_REWRITE);
        }
        Date kill_time=new Date();
        Map <String,Object> map=new HashMap<String, Object>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",kill_time);
        map.put("result",null);
        //执行存储过程，result被复杂
        try {
            seckillDao.killByProdcedure(map);
            //获取result
            Integer result = MapUtils.getInteger(map, "result", -2);//-2表示程序出错
            if(result==1){
                SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
            }else {
                return new SeckillExecution(seckillId,SeckillStateEnum.statOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new SeckillExecution(seckillId,SeckillStateEnum.INNER_KILL);
        }
    }

    private String getMd5(long seckillId){
        String base=seckillId+"/"+slat;
        //Spring提供的MD5工具类
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
