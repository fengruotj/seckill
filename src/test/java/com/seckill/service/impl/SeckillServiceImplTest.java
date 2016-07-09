package com.seckill.service.impl;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.model.Seckill;
import com.seckill.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by dello on 2016/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-applicationContext.xml"})
public class SeckillServiceImplTest {

    private Logger logger= LoggerFactory.getLogger(SeckillServiceImplTest.class);

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> seckills=seckillService.getSeckillList();
        for(Seckill seckill :seckills)
            logger.info(seckill.toString());
    }

    @Test
    public void testGetSeckillById() throws Exception {
        logger.info(seckillService.getSeckillById(1).toString());
    }

//    exposer: Exposer{exposed=true, md5='3d8d6129b0910bc2de263862a7284b66', seckillId=1, now=0, start=0, end=0}
    @Test
    public void testExportSeckillUrl() throws Exception {
        long id=1;
        Exposer exposer = seckillService.exportSeckillUrl(1);
        logger.info("exposer: "+exposer);
    }

    // seckillExecutionSeckillExecution{seckillId=1, state=1, stateInfo='秒杀成功', successKilled=SuccessKilled{, userPhone=1307273, state=0, createTime=Tue Jul 05 20:02:00 CST 2016}}
    @Test
    public void testExecuteSeckill() throws Exception {
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(1, 13072783289L, "3d8d6129b0910bc2de263862a7284b66");
            logger.info("seckillExecution"+seckillExecution);
        }catch (RepeatKillException e1){
            logger.error(e1.getMessage());
        }
        catch (SeckillCloseException e2){
            logger.error(e2.getMessage());
        }
    }

    @Test
    public void testExecuteKill(){
        Exposer exposer = seckillService.exportSeckillUrl(1);
        if(exposer.isExposed()){
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(1, 13072783289L, "3d8d6129b0910bc2de263862a7284b66");
                logger.info("seckillExecution"+seckillExecution);
            }catch (RepeatKillException e1){
                logger.error(e1.getMessage());
            }
            catch (SeckillCloseException e2){
                logger.error(e2.getMessage());
            }
        }else{
            logger.warn("秒杀还没有开启");
        }
    }

    @Test
    public void executeSeckillProcedure(){
        long seckillId=1;
        long phone=123;
        String md5="";
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            md5=exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info("seckillEecution: "+seckillExecution);
        }
    }
}
