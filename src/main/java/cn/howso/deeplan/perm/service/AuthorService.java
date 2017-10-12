package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import cn.howso.deeplan.perm.dto.RoleWithPerms;
import cn.howso.deeplan.perm.mapper.PermMapper;
import cn.howso.deeplan.perm.mapper.RoleMapper;
public class AuthorService {
    @Resource
    private RoleMapper roleMapper;
    @Resource 
    private PermMapper permMapper;
    public List<RoleWithPerms> queryRoles(String username) {
        return roleMapper.queryByUserNameFetchPerms(username);
    }


}
