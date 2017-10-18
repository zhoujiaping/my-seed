package cn.howso.deeplan.perm.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.howso.deeplan.perm.anno.CurrentUser;
import cn.howso.deeplan.perm.cache.RedisCache;
import cn.howso.deeplan.perm.cache.RedisCacheManager;
import cn.howso.deeplan.perm.constant.Const;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.service.UserService;
import cn.howso.deeplan.perm.session.dao.MyShiroSessionRespository;
import cn.howso.deeplan.util.WebUtils;

@Controller
@RequestMapping("users")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    RedisCacheManager redisCacheManager;
    @Resource
    MyShiroSessionRespository sessionResp;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    // @RequiresPermissions(value={"users:create"})
    public Integer add(User user) {
        return userService.add(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer delete(@PathVariable Integer id) {
        return userService.delete(id);
    }

    @RequestMapping(value = "/{id}/authen", method = RequestMethod.PUT)
    public String updateAuthen(@CurrentUser User currentUser, @PathVariable Integer id, String name, String password,
            HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setId(id);
        if (!StringUtils.isEmpty(name)) {
            user.setName(name);
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        boolean isUpdateCurrentUser = Objects.equals(currentUser.getId(), id);
        // 使authen缓存失效
        RedisCache cache = redisCacheManager.getAuthenCache();
        Object key = SecurityUtils.getSubject().getPrincipal();
        cache.remove(key);
        // 使session失效
        Session session = SecurityUtils.getSubject().getSession();
        session.stop();
        // 如果修改的是当前用户的密码，则让用户重新登录
        if (isUpdateCurrentUser) {
            return "redirect:static/login";
        } else {
            Integer count = userService.update(user);
            WebUtils.sendResponse(response, JSONObject.toJSONString(count));
            return null;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Integer update(@PathVariable Integer id, User user) {
        return userService.update(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Integer update(@PathVariable String id, User user) {
        return userService.update(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> query(@CurrentUser User currentUser, User user) {
        return userService.query();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable Integer id) {
        return userService.get(id);
    }

    @RequestMapping(value = "/{userId}/roles-grant", method = RequestMethod.POST)
    @ResponseBody
    public Integer grantRoles(@CurrentUser User currentUser, @PathVariable Integer userId, List<Integer> roleIdList) {
        // 清除缓存的该用户的权限数据，使缓存失效
        RedisCache cache = redisCacheManager.getAuthorCache();
        Object key = SecurityUtils.getSubject().getPrincipals();
        cache.remove(key);
        // 从数据库中查询该用户的权限数据,新的权限数据放入缓存,这个步骤可以不做，shiro会在需要的自动缓存
        return userService.grantRoles(currentUser, userId, roleIdList);
    }

    @RequestMapping(value = "/{userId}/roles-revoke", method = RequestMethod.POST)
    @ResponseBody
    public Integer revokeRoles(@PathVariable Integer userId, ArrayList<Integer> roleIdList) {
        // TODO
        return null;
    }

    @RequestMapping(value = "/{userId}/perms-grant", method = RequestMethod.POST)
    @ResponseBody
    public Integer grantPerms(@PathVariable Integer userId, List<Integer> permIdList) {
        // TODO
        return null;
    }

    @RequestMapping(value = "/{userId}/perms-revoke", method = RequestMethod.POST)
    @ResponseBody
    public Integer revokePerms(@PathVariable Integer userId, List<Integer> permIdList) {
        // TODO
        return null;
    }
}
