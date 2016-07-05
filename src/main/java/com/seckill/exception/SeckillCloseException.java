package com.seckill.exception;

/**
 * Created by dello on 2016/7/5.
 * 秒杀关闭异常
 */
public class SeckillCloseException extends SeckillExcetpion {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
