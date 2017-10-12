 package cn.howso.deeplan.perm.controller;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.service.UserService;
import cn.howso.deeplan.sys.Const;
import cn.howso.deeplan.sys.anno.CurrentUser;

@Controller
@RequestMapping("users")
public class UserController {
    @Resource UserService userService;
    
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value={"users:create"})
    public Integer add(User user){
        return userService.add(user);
    }
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions(value={"users:delete"})
    public Integer delete(@PathVariable Integer id){
        return userService.delete(id);
    }
    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value={"users:update"})
    public Integer update(@PathVariable String id,User user){
        return userService.udpate(user);
    }
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value={"users:view"})
    public List<User> query(@CurrentUser User currentUser){
        System.out.println(currentUser);
        Session s = SecurityUtils.getSubject().getSession();
        Collection<Object> keys = s.getAttributeKeys();
        Object o = s.getAttribute(Const.SESSION_USER_KEY);
        System.out.println(keys);
        return userService.query();
    }
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value={"users:id:view"})
    public User get(@PathVariable Integer id){
        return userService.get(id);
    }
}
