package cn.howso.deeplan.web.account.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.server.account.model.User;
import cn.howso.deeplan.server.log.annotation.LogAnno;

@Controller
@RequestMapping("login")
public class LoginController/* implements InitializingBean,DisposableBean*/{
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    @LogAnno(desc="登录")
    public Object login(User user){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        SecurityUtils.getSubject().login(token);
        return "登录成功";
    }
    /*@Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LoginController had been initialized...");
    }
    @Override
    public void destroy() throws Exception {
        // TODO Auto-generated method stub
        
    }*/
}
