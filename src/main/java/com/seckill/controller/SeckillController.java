package com.seckill.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResult;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.model.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by dello on 2016/7/6.
 */
@Controller
@RequestMapping("/seckill")//url：/模块/资源/{id}/细分/seckill/list
public class SeckillController extends BaseController{

    private Gson gson=new Gson();

    private Logger logger= LoggerFactory.getLogger(SeckillController.class);

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list",seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable Long seckillId, Model model){
        if(seckillId==null){
            return "redirect:/seckill/list";
        }
        Seckill seckillById = seckillService.getSeckillById(seckillId);
        if(seckillById==null){
            return "forward:/seckill/list";
        }
        logger.info("detail");
        model.addAttribute("seckill",seckillById);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String exposer(@PathVariable Long seckillId){
        SeckillResult<Exposer> seckillResult;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            seckillResult=new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage());
            seckillResult=new SeckillResult<Exposer>(false,e.getMessage());
        }
        return gson.toJson(seckillResult);
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String excute(@PathVariable Long seckillId, @PathVariable String md5,
                         @CookieValue(value = "killphone",required = false) Long userPhone){
        if(userPhone==null){
            SeckillResult<SeckillExecution> seckillResult = new SeckillResult<SeckillExecution>(false, "注册");
            return gson.toJson(seckillResult);
        }
        SeckillResult<SeckillExecution> seckillResult;
        try {
            //SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            //改为存储过程去秒杀
            SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
            seckillResult=new SeckillResult<SeckillExecution>(true,seckillExecution);
            return gson.toJson(seckillResult);
        }catch (RepeatKillException e1){
            logger.error(e1.getMessage());
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            seckillResult=new SeckillResult<SeckillExecution>(true,seckillExecution);
            return gson.toJson(seckillResult);
        }
        catch (SeckillCloseException e2){
            logger.error(e2.getMessage());
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.END);
            seckillResult=new SeckillResult<SeckillExecution>(true,seckillExecution);
            return gson.toJson(seckillResult);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            seckillResult=new SeckillResult<SeckillExecution>(true,e.getMessage());
            return gson.toJson(seckillResult);
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String time(){
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        SeckillResult<Date> seckillResult;
        seckillResult=new SeckillResult<Date>(true,new Date());
        return gson.toJson(seckillResult);
    }
}
