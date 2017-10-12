package cn.howso.deeplan.perm.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.service.PermService;

@Controller
@RequestMapping("perms")
public class PermController {
    @Resource private PermService permService;
    
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value={"users:view"})
    public List<User> query(){
        //return userService.query();
        return null;
    }
}
