-- 秒杀存储过程
DELIMITER $$ -- console ;转化为 $
-- 定义存储过程
-- 参数:in 输入参数；  out 输出参数；
-- row_count(); 返回上一条修改类型sql(delete,insert,update)的影响行数
-- row_count()；0，表示未修改数据  >0表示修改的行数  <0:sql错误/未执行修改sql
CREATE PROCEDURE `seckill`.`execute_seckill`
  (in v_seckill_id BIGINT, in v_phone BIGINT, in v_kill_time TIMESTAMP ,out r_reslult int)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION ;
    INSERT IGNORE INTO success_killed(seckill_id, user_phone, create_time)
    VALUES (v_seckill_id,v_phone,v_kill_time);
    SELECT row_count()  INTO insert_count;
    IF (insert_count=0)THEN
      ROLLBACK;
      SET  r_reslult=-1;
    ELSEIF (insert_count<0) THEN
      ROLLBACK;
      SET  r_reslult=-2;
    ELSE
      UPDATE seckill set number=number-1
      WHERE id=v_seckill_id
            AND end_time>v_kill_time
            AND start_time<v_kill_time
            AND seckill.number>0;
      SELECT row_count()  INTO insert_count;
      IF (insert_count=0)THEN
        ROLLBACK;
        SET  r_reslult=0;
      ELSEIF (insert_count<0) THEN
        ROLLBACK;
        SET  r_reslult=-2;
      ELSE
        COMMIT ;
        set r_reslult=1;
      END IF;
    END IF;
  END;
$$


-- 表示存储过程定义结束
DELIMITER ; -- console ;转化为 ;
SET @r_reslult=-3;-- 定义变量
CALL execute_seckill(1,'123456789',now(),@r_reslult);

SELECT @r_reslult;

-- 存储过程
-- 1：存储过程优化:事务行级锁持有时间
-- 2：不要过度依赖存储过程
-- 3：简单的逻辑可以依赖存储过程
-- 4：QPS:一个秒杀单接近6000+左右的秒杀
