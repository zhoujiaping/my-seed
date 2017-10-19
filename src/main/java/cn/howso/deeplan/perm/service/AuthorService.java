package cn.howso.deeplan.perm.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.cache.RedisCache;
import cn.howso.deeplan.perm.dto.RoleWithPerms;
import cn.howso.deeplan.perm.mapper.PermMapper;
import cn.howso.deeplan.perm.mapper.RoleMapper;
import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.model.Perm;
@Service
public class AuthorService {
    @Resource
    private RoleMapper roleMapper;
    @Resource 
    private PermMapper permMapper;
    @Resource 
    private UserMapper userMapper;
    @Resource(name="authorCache")
    private RedisCache cache;
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
    public void removeCache(String username){
        // 清除缓存的该用户的权限数据，使缓存失效
        Set<Object> keys = cache.keys();
        for(Object key:keys){
            PrincipalCollection principal = (PrincipalCollection) key;
            Object usernameInCache = principal.getPrimaryPrincipal();
            if(username.equals(usernameInCache)){
                cache.remove(key);
            }
        }
    }

}
