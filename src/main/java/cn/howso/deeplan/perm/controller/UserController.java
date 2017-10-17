 package cn.howso.deeplan.perm.controller;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.perm.anno.CurrentUser;
import cn.howso.deeplan.perm.cache.RedisCache;
import cn.howso.deeplan.perm.cache.RedisCacheManager;
import cn.howso.deeplan.perm.constant.Const;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.service.UserService;
import cn.howso.deeplan.perm.session.dao.MyShiroSessionRespository;

@Controller
@RequestMapping("users")
public class UserController {
    @Resource UserService userService;
    @Resource RedisCacheManager redisCacheManager;
    @Resource MyShiroSessionRespository sessionResp;
    
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    //@RequiresPermissions(value={"users:create"})
    public Integer add(User user){
        return userService.add(user);
    }
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    //@RequiresPermissions(value={"users:delete"})
    public Integer delete(@PathVariable Integer id){
        return userService.delete(id);
    }
    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    @ResponseBody
    //@RequiresPermissions(value={"users:updateOne"})
    public Integer update(@PathVariable String id,User user){
        return userService.udpate(user);
    }
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    //@RequiresPermissions(value={"users:view"})
    public List<User> query(@CurrentUser User currentUser,User user){
    	PrincipalCollection arg0 = 	SecurityUtils.getSubject().getPrincipals();
        Collection<Session> sessions = sessionResp.getAllSessions();
        sessions.forEach(x->{
            System.out.println(x.getId());
        });
        
        RedisCache authenCache = redisCacheManager.getAuthenCache();
        Class<?> clazz = authenCache.getClass();
        Type[] types = clazz.getTypeParameters();
        Set<?> authenKeys = authenCache.keys();
        authenKeys.forEach(key->{
            //Object v = authenCache.get(key);
        });
        RedisCache authorCache = redisCacheManager.getAuthorCache();
        Set<?> authorKeys = authorCache.keys();
        authorKeys.forEach(System.out::println);
        
        System.out.println(currentUser);
        Session s = SecurityUtils.getSubject().getSession();
        Collection<Object> keys = s.getAttributeKeys();
        Object o = s.getAttribute(Const.SESSION_USER_KEY);
        System.out.println(keys);
        return userService.query();
    }
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    @ResponseBody
    //@RequiresPermissions(value={"users:viewOne"})
    public User get(@PathVariable Integer id){
        return userService.get(id);
    }
}
