package cn.howso.deeplan.perm.controller;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.howso.deeplan.perm.anno.CurrentUser;
import cn.howso.deeplan.perm.constant.Const;
import cn.howso.deeplan.perm.model.Role;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.realm.MyRealm;
import cn.howso.deeplan.perm.service.AuthenService;
import cn.howso.deeplan.perm.service.AuthorService;
import cn.howso.deeplan.perm.service.UserService;
import cn.howso.deeplan.perm.session.dao.MyShiroSessionRespository;
import cn.howso.deeplan.util.WebUtils;

@Controller
@RequestMapping("users")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private AuthorService authorService;
    @Resource
    private MyShiroSessionRespository sessionResp;
    @Resource
    private AuthenService authenService;
    @Resource
    private MyRealm realm;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    // @RequiresPermissions(value={"users:create"})
    public Integer add(User user) {
        return userService.add(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer delete(@PathVariable Integer id,Integer _permSpaceId) {
        return userService.delete(id,_permSpaceId);
    }
    /**
     * 修改用户名密码
     */
    @RequestMapping(value = "/{userId}/authen", method = RequestMethod.PUT)
    public String updateAuthen(@CurrentUser User currentUser, @PathVariable Integer userId, String name, String password,
            Integer _permSpaceId,
            HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setId(userId);
        if (!StringUtils.isEmpty(name)) {
            user.setName(name);
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        // 使authen缓存失效
        User record = userService.get(userId);
        Assert.isTrue(record!=null,"用户不存在");
        authenService.removeCache(record.getName());
        stopSessionByUsername(record.getName());
        // 如果修改的是当前用户的密码，则让用户重新登录
        boolean isUpdateCurrentUser = Objects.equals(currentUser.getId(), userId);
        if (isUpdateCurrentUser) {
         // 使session失效
            return "redirect:static/login";
        } else {
            Integer count = userService.update(user,_permSpaceId);
            WebUtils.sendResponse(response, JSONObject.toJSONString(count));
            return null;
        }
    }
    private void stopSessionByUsername(String username) {
        Collection<Session> sessions = sessionResp.getAllSessions();
        for(Session s:sessions){
            User user = (User) s.getAttribute(Const.SESSION_USER_KEY);
            if(Objects.equals(user.getName(), username)){
                s.stop();
            }
        }
    }
    /**
     * 修改其他字段
     * */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Integer update(@PathVariable Integer id, User user,Integer _permSpaceId) {
        user.setName(null);
        user.setPassword(null);
        return userService.update(user,_permSpaceId);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> query(@CurrentUser User currentUser, User user,Integer _permSpaceId) {
        return userService.query();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable Integer id,Integer _permSpaceId,List<Role> roleList) {
        return userService.get(id);
    }

    @RequestMapping(value = "/{userId}/roles-grant", method = RequestMethod.POST)
    @ResponseBody
    public Integer grantRoles(@CurrentUser User currentUser,Integer _permSpaceId,@PathVariable Integer userId, List<Integer> roleIdList) {
        User user = userService.get(userId);
        Assert.isTrue(user!=null,"用户不存在");
        // 清除缓存的该用户的权限数据，使缓存失效
        authorService.removeCache(user.getName());
        // 从数据库中查询该用户的权限数据,新的权限数据放入缓存,这个步骤可以不做，shiro会在需要的自动缓存
        return userService.grantRoles(currentUser,_permSpaceId, userId, roleIdList);
    }

    @RequestMapping(value = "/{userId}/roles-revoke", method = RequestMethod.POST)
    @ResponseBody
    public Integer revokeRoles(@CurrentUser User currentUser,Integer _permSpaceId,@PathVariable Integer userId, List<Integer> roleIdList) {
        User user = userService.get(userId);
        Assert.isTrue(user!=null,"用户不存在");
        // 清除缓存的该用户的权限数据，使缓存失效
        authorService.removeCache(user.getName());
        return userService.revokeRoles(currentUser, _permSpaceId,userId, roleIdList);
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
