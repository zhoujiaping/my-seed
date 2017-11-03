package cn.howso.deeplan.perm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.dto.RoleWithPerms;
import cn.howso.deeplan.perm.mapper.MenuMapper;
import cn.howso.deeplan.perm.mapper.PermMapper;
import cn.howso.deeplan.perm.mapper.RoleMapper;
import cn.howso.deeplan.perm.mapper.RoleMenuMapper;
import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.model.Perm;
import cn.howso.deeplan.perm.model.Role;
import cn.howso.deeplan.perm.model.RoleMenu;
import cn.howso.deeplan.perm.model.User;
import cn.howso.mybatis.model.Example;
@Service
public class AuthorService {
    @Resource
    private RoleMapper roleMapper;
    @Resource 
    private PermMapper permMapper;
    @Resource 
    private UserMapper userMapper;
    /*@Resource(name="authorCache")
    private RedisCache cache;*/
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    /**
     * 用户的角色，角色的权限
     * */
    public List<RoleWithPerms> queryByUserNameFetchPerms(String username) {
        return roleMapper.queryByUserNameFetchPerms(username);
    }
    /**
     * 用户的权限
     * */
    public List<Perm> queryUserPerms(String username){
        return permMapper.queryUserPerms(username);
    }
    /*public void removeCache(String username){
        // 清除缓存的该用户的权限数据，使缓存失效
        Set<Object> keys = cache.keys();
        for(Object key:keys){
            PrincipalCollection principal = (PrincipalCollection) key;
            Object usernameInCache = principal.getPrimaryPrincipal();
            if(username.equals(usernameInCache)){
                cache.remove(key);
            }
        }
    }*/
    /*public void removeAllCache() {
        Set<Object> keys = cache.keys();
        for(Object key:keys){
            cache.remove(key);
        }
    }*/
    public Set<String> queryRoleNames(String username) {
        List<RoleWithPerms> roles = roleMapper.queryByUserNameFetchPerms(username);
        Set<String> roleNames = roles.stream().map(r->{
            return r.getRolename();
        }).collect(Collectors.toSet());
        return roleNames;
    }
    public Set<String> queryPermStrings(String username) {
        Set<String> permStrings = new HashSet<>();
        List<RoleWithPerms> roles = roleMapper.queryByUserNameFetchPerms(username);
        List<Perm> userPerms = permMapper.queryUserPerms(username);
        if(userPerms.isEmpty()){
            roles.forEach(r->{
                r.getPerms().forEach(p->{
                    permStrings.add(p.getPermstring());
                });
            });
        }else{
            userPerms.forEach(p->{
                permStrings.add(p.getPermstring());
            });
        }        
        return permStrings;
    }
    public boolean hasRole(Integer roleId){
        Role role = roleMapper.selectByPrimaryKey(roleId);
        if(role==null){
            return false;
        }
        return SecurityUtils.getSubject().hasRole(role.getRolename());
    }
    public boolean hasAllRoles(List<Integer> roleIds){
        Example example = new Example();
        example.createCriteria().and("id").in(roleIds);
        List<Role> roles = roleMapper.selectByExample(example);
        Set<String> rolenames = roles.stream().map(role->role.getRolename()).collect(Collectors.toSet());
        return SecurityUtils.getSubject().hasAllRoles(rolenames);
    }
    public boolean hasPerm(Integer permId){
        Perm perm = permMapper.selectByPrimaryKey(permId);
        if(perm==null){
            return false;
        }
        return SecurityUtils.getSubject().isPermitted(perm.getPermstring());
    }
    public boolean hasAllPerms(List<Integer> permIds){
        Example example = new Example();
        example.createCriteria().and("id").in(permIds);
        List<Perm> perms = permMapper.selectByExample(example);
        String[] permStrings = perms.stream().map(perm->perm.getPermstring()).collect(Collectors.toSet())
                .toArray(new String[]{});
        return SecurityUtils.getSubject().isPermittedAll(permStrings);
    }
    public boolean hasAllMenus(User user,List<Integer> menuIdList) {
        List<Role> roles = roleMapper.queryByUserName(user.getName());
        List<Integer> roleids = roles.stream().map(role->role.getId()).collect(Collectors.toList());
        Example example = new Example();
        example.createCriteria().and("role_id").in(roleids).and("menu_id").in(menuIdList);
        List<RoleMenu> rolemenus = roleMenuMapper.selectByExample(example);
        Set<Integer> menuidsInDb = rolemenus.stream().map(x->x.getMenuId()).collect(Collectors.toSet());
        Set<Integer> menuids = new HashSet<>();
        menuids.addAll(menuIdList);
        return menuidsInDb.size()>=menuids.size();
    }
}
