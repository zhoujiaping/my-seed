package cn.howso.deeplan.perm.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.framework.exception.BusinessException;
import cn.howso.deeplan.perm.model.User;

@Controller
@RequestMapping("/")
public class AuthenController{
    @Resource private DefaultWebSessionManager sessionManager;
    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ResponseBody
    public Object login(User user,HttpServletResponse response){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        try{
            SecurityUtils.getSubject().login(token);
        }catch(AccountException e){
            throw new BusinessException(e.getMessage());
        }
        return "登录成功";
    }
    @RequestMapping(value="/logout",method=RequestMethod.POST)
    @ResponseBody
    public Object logout(User user){
        SecurityUtils.getSubject().logout();
        return "注销成功";
    }
}
