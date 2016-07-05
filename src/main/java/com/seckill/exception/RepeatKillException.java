package com.seckill.exception;

/**
 * Created by dello on 2016/7/5.
 *  重复秒杀异常(运行期异常)
 */
public class RepeatKillException extends SeckillExcetpion{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
