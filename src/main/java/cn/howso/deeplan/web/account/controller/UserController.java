package cn.howso.deeplan.web.account.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.framework.model.AjaxResult;
import cn.howso.deeplan.server.account.model.User;
import cn.howso.deeplan.server.account.service.UserService;

@Controller
@RequestMapping("perm/user")
public class UserController {
    @Resource UserService userService;
    
    @RequestMapping("")
    public String index(){
        return "permission/user";
    }
    @RequestMapping("add")
    @ResponseBody
    @RequiresPermissions("user:create")
    public AjaxResult add(User user){
        return new AjaxResult("创建用户成功");
    }
}
