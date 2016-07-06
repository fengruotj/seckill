package com.seckill.controller;

import com.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by dello on 2016/7/6.
 */
@Controller
public class BaseController {
    @Autowired
    protected SeckillService seckillService;
}
