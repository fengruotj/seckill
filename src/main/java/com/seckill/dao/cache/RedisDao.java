package com.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.model.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by dello on 2016/7/9.
 */
public class RedisDao {
    private Logger logger= LoggerFactory.getLogger(RedisDao.class);

    private JedisPool jedisPool;

    private RuntimeSchema<Seckill> runtimeSchema=RuntimeSchema.createFrom(Seckill.class);

    //初始化JedisPool
    public RedisDao(String ip,int port){
        jedisPool=new JedisPool(ip,port);
    }

    public Seckill getSeckill(long seckillId){
        Jedis jedis=jedisPool.getResource();
        try {
            String key="seckilId:"+seckillId;
            //并没有实现内部序列化
            //get-byte[];->反序列化->Object(kill)
            //自定义的序列化方式
            byte[] bytes = jedis.get(key.getBytes());
            if(bytes!=null){
                Seckill seckill=runtimeSchema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes,seckill,runtimeSchema);
                return seckill;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            jedis.close();
        }
        return null;
    }

    public String putSeckill(Seckill seckill){
        Jedis jedis=jedisPool.getResource();
        try {
            String key="seckilId:"+seckill.getId();
            byte[] bytes=ProtostuffIOUtil.toByteArray(seckill,runtimeSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            String setex = jedis.setex(key.getBytes(), 60 * 60, bytes);
            return setex;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return "";
    }
}
