package cn.howso.deeplan.perm.controller;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.howso.deeplan.framework.model.R;
import cn.howso.deeplan.log.annotation.LogAnno;
import cn.howso.deeplan.perm.anno.CurrentUser;
import cn.howso.deeplan.perm.model.Role;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.realm.MyRealm;
import cn.howso.deeplan.perm.service.AuthenService;
import cn.howso.deeplan.perm.service.AuthorService;
import cn.howso.deeplan.perm.service.UserService;
import cn.howso.deeplan.perm.session.dao.RedisSessionDao;
import cn.howso.deeplan.util.WebUtils;

@Controller
@RequestMapping("users")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private AuthorService authorService;
    /*@Resource
    private MyShiroSessionRespository sessionResp;*/
    @Resource
    private RedisSessionDao sessionDao;
    @Resource
    private AuthenService authenService;
    @Resource
    private MyRealm realm;

    @RequestMapping(value="",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value={"users:create"})
    @LogAnno(ignore="password")
    public R add(User user,Integer _permSpaceId) {
        return R.ok().set("count",userService.add(user,_permSpaceId));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions("users:id:delete")
    @LogAnno
    public R delete(@PathVariable Integer id,Integer _permSpaceId) {
        return R.ok().set("count",userService.delete(id,_permSpaceId));
    }
    /**
     * 修改用户名密码
     */
    @RequestMapping(value = "{userId}/authen", method = RequestMethod.PUT)
    @RequiresPermissions("users:id:authen-update")
    @LogAnno(ignore="password")
    @ResponseBody
    public R updateAuthen(@CurrentUser User currentUser, @PathVariable Integer userId, String name, String password,
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
        User record = userService.get(userId,_permSpaceId);
        Assert.isTrue(record!=null,"用户不存在");
        //authenService.removeCache(record.getName());
        // 如果修改的是当前用户的密码，则让session失效并且让用户重新登录
        Integer count = userService.update(user,_permSpaceId);
        boolean isUpdateCurrentUser = Objects.equals(currentUser.getId(), userId);
        R r = R.ok("修改认证信息成功");
        return r;
        /*if (isUpdateCurrentUser) {
        	sessionDao.delete(SecurityUtils.getSubject().getSession());
         // 使session失效
            return "redirect:static/login/login.html";
        } else {
            WebUtils.sendResponse(response, JSONObject.toJSONString(count));
            return null;
        }*/
    }
    /**
     * 修改其他字段
     * */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    @RequiresPermissions("users:id:update")
    @LogAnno
    public R update(@PathVariable Integer id, User user,Integer _permSpaceId) {
        user.setName(null);
        user.setPassword(null);
        return R.ok().set("count",userService.update(user,_permSpaceId));
    }

    @RequestMapping(value="",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("users:query")
    @LogAnno
    public R query(@CurrentUser User currentUser, User user,Integer _permSpaceId) {
        return R.ok().set("users", userService.query(_permSpaceId));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("users:id:query")
    @LogAnno
    public R get(@PathVariable Integer id,Integer _permSpaceId,List<Role> roleList) {
        return R.ok().set("user", userService.get(id,_permSpaceId));
    }

    @RequestMapping(value = "{userId}/roles-grant", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("users:id:roles-grant")
    @LogAnno
    public R grantRoles(@CurrentUser User currentUser,Integer _permSpaceId,@PathVariable Integer userId, List<Integer> roleIdList) {
        User user = userService.get(userId,_permSpaceId);
        Assert.isTrue(user!=null,"用户不存在");
        Assert.isTrue(authorService.hasAllRoles(roleIdList),"当前用户必须拥有这些角色");
        // 清除缓存的该用户的权限数据，使缓存失效
        //authorService.removeCache(user.getName());
        // 从数据库中查询该用户的权限数据,新的权限数据放入缓存,这个步骤可以不做，shiro会在需要的自动缓存
        return R.ok().set("count", userService.grantRoles(user, roleIdList));
    }

    @RequestMapping(value = "{userId}/roles-revoke", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("users:id:roles-revoke")
    @LogAnno
    public R revokeRoles(@CurrentUser User currentUser,Integer _permSpaceId,@PathVariable Integer userId, List<Integer> roleIdList) {
        User user = userService.get(userId,_permSpaceId);
        Assert.isTrue(user!=null,"用户不存在");
        // 清除缓存的该用户的权限数据，使缓存失效
        Assert.isTrue(authorService.hasAllRoles(roleIdList),"当前用户必须拥有这些角色");
        //authorService.removeCache(user.getName());
        return R.ok().set("count", userService.revokeRoles(user, roleIdList));
    }

    @RequestMapping(value = "{userId}/perms-grant", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("users:id:prems-grant")
    @LogAnno
    public R grantPerms(@PathVariable Integer userId,Integer _permSpaceId, List<Integer> permIdList) {
        User user = userService.get(userId, _permSpaceId);
        Assert.isTrue(user!=null,"用户不存在");
        Assert.isTrue(authorService.hasAllPerms(permIdList),"当前用户必须拥有这些权限");
        //authorService.removeCache(user.getName());
        return R.ok().set("count",userService.grantPerms(user,_permSpaceId,permIdList));
    }

    @RequestMapping(value = "{userId}/perms-revoke", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("users:id:perms-revoke")
    @LogAnno
    public R revokePerms(@PathVariable Integer userId,Integer _permSpaceId, List<Integer> permIdList) {
        User user = userService.get(userId, _permSpaceId);
        Assert.isTrue(user!=null,"用户不存在");
        Assert.isTrue(authorService.hasAllPerms(permIdList),"当前用户必须拥有这些权限");
        //authorService.removeCache(user.getName());
        return R.ok().set("count", userService.revokePerms(user,_permSpaceId,permIdList));
    }
}
