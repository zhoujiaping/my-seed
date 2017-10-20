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
    public Integer add(Role role,Integer _permSpaceId){
        return roleService.add(role,_permSpaceId);
    }
    @RequestMapping(value="{id}",method=RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions("roles:id:delete")
    @LogAnno
    public Integer delete(@PathVariable Integer id,Integer _permSpaceId){
        return roleService.delete(id,_permSpaceId);
    }
    @RequestMapping(value="{id}",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:update")
    @LogAnno
    public Integer update(@PathVariable String id,Role role,Integer _permSpaceId){
        return roleService.udpate(role,_permSpaceId);
    }
    @RequestMapping(value="",method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("roles:query")
    @LogAnno
    public List<Role> query(Integer _permSpaceId){
        return roleService.query(_permSpaceId);
    }
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("roles:id:query")
    @LogAnno
    public Role get(@PathVariable Integer id,Integer _permSpaceId){
        return roleService.get(id,_permSpaceId);
    }
    
    @RequestMapping(value="{roleId}/perms-grant",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:perms-grant")
    @LogAnno
    public Integer grantPerms(@CurrentUser User currentUser,@PathVariable Integer roleId,Integer _permSpaceId,List<Integer> permIdList){
        Role role = roleService.get(roleId,_permSpaceId);
        Assert.isTrue(role!=null,"角色不存在");
        Assert.isTrue(authorService.hasRole(roleId),"当前用户必须拥有该角色");
        Assert.isTrue(authorService.hasAllPerms(permIdList),"当前用户必须拥有这些权限");
        authorService.removeAllCache();
        return roleService.grantPerms(roleId,permIdList);
    }
    @RequestMapping(value="{roleId}/perms-revoke",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:perms-revoke")
    @LogAnno
    public Integer revokePerms(@PathVariable Integer roleId,Integer _permSpaceId,List<Integer> permIdList){
        Role role = roleService.get(roleId,_permSpaceId);
        Assert.isTrue(role!=null,"角色不存在");
        Assert.isTrue(authorService.hasRole(roleId),"当前用户必须拥有该角色");
        Assert.isTrue(authorService.hasAllPerms(permIdList),"当前用户必须拥有这些权限");
        authorService.removeAllCache();
        return roleService.revokePerms(roleId,permIdList);
    }
    
    @RequestMapping(value="{roleId}/menus-grant",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:menus-grant")
    @LogAnno
    public Integer grantMenus(@CurrentUser User currentUser,@PathVariable Integer roleId,Integer _permSpaceId,List<Integer> menuIdList){
        Role role = roleService.get(roleId,_permSpaceId);
        Assert.isTrue(role!=null,"角色不存在");
        Assert.isTrue(authorService.hasRole(roleId),"当前用户必须拥有该角色");
        Assert.isTrue(authorService.hasAllMenus(currentUser,menuIdList),"当前用户必须拥有这些菜单");
        return menuService.grantMenus(roleId,menuIdList);
    }
    @RequestMapping(value="{roleId}/menus-revoke",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("roles:id:menus-revoke")
    @LogAnno
    public Integer revokeMenus(@CurrentUser User currentUser,@PathVariable Integer roleId,Integer _permSpaceId,List<Integer> menuIdList){
        Role role = roleService.get(roleId,_permSpaceId);
        Assert.isTrue(role!=null,"角色不存在");
        Assert.isTrue(authorService.hasRole(roleId),"当前用户必须拥有该角色");
        Assert.isTrue(authorService.hasAllMenus(currentUser,menuIdList),"当前用户必须拥有这些菜单");
        return menuService.revokeMenus(roleId,menuIdList);
    }
}
