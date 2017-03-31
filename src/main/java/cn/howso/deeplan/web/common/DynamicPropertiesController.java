package cn.howso.deeplan.web.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.constant.DynamicProperties;

/**
 * 该功能的目标在于，将系统中的业务常量统一管理，并且可以动态控制而不用重启系统。
 * @ClassName DynamicPropertiesController
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @Date 2017年3月18日 下午1:36:09
 * @version 1.0.0
 */
@Controller
@RequestMapping("common/dyn-prop")
public class DynamicPropertiesController {
    @Resource DynamicProperties dynamicProperties;
    @ResponseBody
    @RequestMapping("get")
    public Object doGet(String key){
        return dynamicProperties.get(key);
    }
    @ResponseBody
    @RequestMapping("refresh")
    public Object refresh(){
        dynamicProperties.refresh();
        return null;
    }
}
