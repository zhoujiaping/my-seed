package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import cn.howso.deeplan.perm.mapper.RoleMapper;
import cn.howso.deeplan.perm.mapper.SysUserRoleMapper;
import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.model.Role;

public class RoleService {
    @Resource RoleMapper roleMapper;
    @Resource UserMapper userMapper;
    @Resource SysUserRoleMapper sysUserRoleMapper;
    public List<Role> queryByUserName(String username) {
       /* UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        Assert.isTrue(users.size()==1);
        SysUserRoleExample userRoleExample = new SysUserRoleExample();
        userRoleExample.createCriteria().andUserIdEqualTo(users.get(0).getId());
        List<SysUserRole> userRoles = sysUserRoleMapper.selectByExample(userRoleExample );
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andIdIn(userRoles.stream().map(x->x.getRoleId()).collect(Collectors.toList()));
        return roleMapper.selectByExample(roleExample );*/
        return roleMapper.queryByUserName(username);
    }
}
