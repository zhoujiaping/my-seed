package cn.howso.deeplan.web.crowd.report.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.howso.deeplan.framework.model.AjaxResult;
import cn.howso.deeplan.server.module1.service.UrbanService;
import cn.howso.deeplan.util.LogUtil;

@RequestMapping("urban")
@Controller
public class UrbanController {
    @Resource UrbanService urbanService;
    
    @RequestMapping("test")
    @ResponseBody
    public AjaxResult test(){
        LogUtil.getLogger().info("log4j2 test {}", System.currentTimeMillis());
        return new AjaxResult(urbanService.queryByPage());
        //return null;
    }
}
