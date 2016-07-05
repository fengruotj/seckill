package com.seckill.exception;

/**
 * Created by dello on 2016/7/5.
 *  秒杀通用异常
 */
public class SeckillExcetpion extends RuntimeException {

    public SeckillExcetpion(String message) {
        super(message);
    }

    public SeckillExcetpion(String message, Throwable cause) {
        super(message, cause);
    }
}
