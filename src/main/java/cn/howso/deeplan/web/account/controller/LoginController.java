package cn.howso.deeplan.web.account.controller;

import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import cn.howso.deeplan.framework.model.AjaxResult;
import cn.howso.deeplan.server.account.model.User;
import cn.howso.deeplan.server.log.annotation.LogAnno;

@Controller
@RequestMapping("perm/login")
public class LoginController implements InitializingBean,DisposableBean{
    @RequestMapping(value="")
    public String index(Integer p){
        System.out.println(p);
        return "authen/login";
    }
    @RequestMapping("login")
    @ResponseBody
    @LogAnno(desc="登录")
    public AjaxResult login(User user){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        SecurityUtils.getSubject().login(token);
        return new AjaxResult("登录成功");
    }
    @RequestMapping("logout")
    @ResponseBody
    @LogAnno(desc="注销")
    public AjaxResult logout(User user){
        SecurityUtils.getSubject().logout();
        return new AjaxResult("注销成功");
    }
    @RequestMapping("import")
    @ResponseBody
    @LogAnno(desc="测试")
    public AjaxResult test(String name,@RequestPart MultipartFile file) throws IOException{
        System.out.println(name);
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        System.out.println(new String(file.getBytes()));
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        ParameterizableViewController pvc;
        return new AjaxResult();
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LoginController had been initialized...");
    }
    @Override
    public void destroy() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
