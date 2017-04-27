package cn.howso.deeplan.perm.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.log.annotation.LogAnno;
import cn.howso.deeplan.perm.model.User;
@Controller
@RequestMapping("logout")
public class LogoutController {
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    @LogAnno(desc="注销")
    public Object logout(User user){
        SecurityUtils.getSubject().logout();
        return "注销成功";
    }
}
