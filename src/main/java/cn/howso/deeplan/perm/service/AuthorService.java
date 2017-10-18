package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.dto.RoleWithPerms;
import cn.howso.deeplan.perm.mapper.PermMapper;
import cn.howso.deeplan.perm.mapper.RoleMapper;
import cn.howso.deeplan.perm.model.Perm;
@Service
public class AuthorService {
    @Resource
    private RoleMapper roleMapper;
    @Resource 
    private PermMapper permMapper;
    /**
     * 用户的角色，角色的权限
     * */
    public List<RoleWithPerms> queryRoles(String username) {
        return roleMapper.queryByUserNameFetchPerms(username);
    }
    /**
     * 用户的权限
     * */
    public List<Perm> queryUserPerms(String username){
        return permMapper.queryUserPerms(username);
    }


}
