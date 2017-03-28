package cn.howso.deeplan.server.account.realm;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.howso.deeplan.server.account.model.Permission;
import cn.howso.deeplan.server.account.model.Role;
import cn.howso.deeplan.server.account.model.User;
import cn.howso.deeplan.server.account.service.PermissionService;
import cn.howso.deeplan.server.account.service.RoleService;
import cn.howso.deeplan.server.account.service.UserService;

public class MyRealm extends AuthorizingRealm {

    private UserService userService;
    private RoleService roleService;
    private PermissionService permissionService;
    
    public UserService getUserService() {
        return userService;
    }
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    public RoleService getRoleService() {
        return roleService;
    }
    
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    
    public PermissionService getPermissionService() {
        return permissionService;
    }
    
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @Override
    /**
     * 获得授权信息
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> roles = roleService.queryByUserName(username);
        Set<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
        info.addRoles(roleNames);
        List<Permission> perms = permissionService.queryByUserName(username);
        Set<String> permStrings = perms.stream().map(Permission::getPattern).distinct().collect(Collectors.toSet());
        info.addStringPermissions(permStrings);
        return info;
    }

    @Override
    /**
     * 获得认证信息认证
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        User authenticatedUser = userService.authen(user);
        if (authenticatedUser == null) {
            throw new AccountException("没登录");
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
