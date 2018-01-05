package cn.howso.deeplan.perm.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.framework.model.R;
import cn.howso.deeplan.log.annotation.LogAnno;
import cn.howso.deeplan.perm.anno.CurrentUser;
import cn.howso.deeplan.perm.model.Role;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.service.AuthorService;
import cn.howso.deeplan.perm.service.MenuService;
import cn.howso.deeplan.perm.service.RoleService;

@Controller
@RequestMapping("roles")
public class RoleController {
    @Resource 
    private RoleService roleService;
    @Resource
    private AuthorService authorService;
    @Resource
    private MenuService menuService;
    
    @RequestMapping(value="",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:create")
    @LogAnno
    public R add(Role role,Integer _permSpaceId){
        return R.ok("添加成功").set("count", roleService.add(role,_permSpaceId));
    }
    @RequestMapping(value="{id}",method=RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions("roles:id:delete")
    @LogAnno
    public R delete(@PathVariable Integer id,Integer _permSpaceId){
        return R.ok("删除成功").set("count", roleService.delete(id,_permSpaceId));
    }
    @RequestMapping(value="{id}",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:update")
    @LogAnno
    public R update(@PathVariable String id,Role role,Integer _permSpaceId){
        return R.ok("更新成功").set("count", roleService.udpate(role,_permSpaceId));
    }
    @RequestMapping(value="",method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("roles:query")
    @LogAnno
    public R query(Integer _permSpaceId){
        return R.ok().set("roles",roleService.query(_permSpaceId));
    }
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("roles:id:query")
    @LogAnno
    public R get(@PathVariable Integer id,Integer _permSpaceId){
        return R.ok().set("role", roleService.get(id,_permSpaceId));
    }
    
    @RequestMapping(value="{roleId}/perms-grant",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:perms-grant")
    @LogAnno
    public R grantPerms(@CurrentUser User currentUser,@PathVariable Integer roleId,Integer _permSpaceId,List<Integer> permIdList){
        Role role = roleService.get(roleId,_permSpaceId);
        Assert.isTrue(role!=null,"角色不存在");
        Assert.isTrue(authorService.hasRole(roleId),"当前用户必须拥有该角色");
        Assert.isTrue(authorService.hasAllPerms(permIdList),"当前用户必须拥有这些权限");
        //authorService.removeAllCache();
        return R.ok("授权成功").set("count",roleService.grantPerms(roleId,permIdList));
    }
    @RequestMapping(value="{roleId}/perms-revoke",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:perms-revoke")
    @LogAnno
    public R revokePerms(@PathVariable Integer roleId,Integer _permSpaceId,List<Integer> permIdList){
        Role role = roleService.get(roleId,_permSpaceId);
        Assert.isTrue(role!=null,"角色不存在");
        Assert.isTrue(authorService.hasRole(roleId),"当前用户必须拥有该角色");
        Assert.isTrue(authorService.hasAllPerms(permIdList),"当前用户必须拥有这些权限");
        //authorService.removeAllCache();
        return R.ok().set("count", roleService.revokePerms(roleId,permIdList));
    }
    
    @RequestMapping(value="{roleId}/menus-grant",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:menus-grant")
    @LogAnno
    public R grantMenus(@CurrentUser User currentUser,@PathVariable Integer roleId,Integer _permSpaceId,List<Integer> menuIdList){
        Role role = roleService.get(roleId,_permSpaceId);
        Assert.isTrue(role!=null,"角色不存在");
        Assert.isTrue(authorService.hasRole(roleId),"当前用户必须拥有该角色");
        Assert.isTrue(authorService.hasAllMenus(currentUser,menuIdList),"当前用户必须拥有这些菜单");
        return R.ok().set("count",menuService.grantMenus(roleId,menuIdList));
    }
    @RequestMapping(value="{roleId}/menus-revoke",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:menus-revoke")
    @LogAnno
    public R revokeMenus(@CurrentUser User currentUser,@PathVariable Integer roleId,Integer _permSpaceId,List<Integer> menuIdList){
        Role role = roleService.get(roleId,_permSpaceId);
        Assert.isTrue(role!=null,"角色不存在");
        Assert.isTrue(authorService.hasRole(roleId),"当前用户必须拥有该角色");
        Assert.isTrue(authorService.hasAllMenus(currentUser,menuIdList),"当前用户必须拥有这些菜单");
        return R.ok().set("count", menuService.revokeMenus(roleId,menuIdList));
    }
}
