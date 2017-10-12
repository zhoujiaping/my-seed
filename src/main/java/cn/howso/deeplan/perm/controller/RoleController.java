package cn.howso.deeplan.perm.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.howso.deeplan.perm.model.Role;
import cn.howso.deeplan.perm.service.RoleService;

@Controller
@RequestMapping("roles")
public class RoleController {
    @Resource private RoleService roleService;
    
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value={"roles:create"})
    public Integer add(Role role){
        return roleService.add(role);
    }
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions(value={"roles:delete"})
    public Integer delete(@PathVariable Integer id){
        return roleService.delete(id);
    }
    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value={"roles:update"})
    public Integer update(@PathVariable String id,Role role){
        return roleService.udpate(role);
    }
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value={"roles:view"})
    public List<Role> query(){
        return roleService.query();
    }
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value={"roles:id:view"})
    public Role get(@PathVariable Integer id){
        return roleService.get(id);
    }
}
