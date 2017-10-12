package cn.howso.deeplan.sys.realm;

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

import cn.howso.deeplan.perm.dto.RoleWithPerms;
import cn.howso.deeplan.perm.model.Perm;
import cn.howso.deeplan.perm.model.Role;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.service.AuthenService;
import cn.howso.deeplan.perm.service.AuthorService;

public class MyRealm extends AuthorizingRealm {

    private AuthenService authenService;
    private AuthorService authorService;
    
    
    public AuthenService getAuthenService() {
        return authenService;
    }
    
    public void setAuthenService(AuthenService authenService) {
        this.authenService = authenService;
    }
    
    public AuthorService getAuthorService() {
        return authorService;
    }
    
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }
    @Override
    /**
     * 获得授权信息
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<RoleWithPerms> roles = authorService.queryRoles(username);
        //List<Role> roles = roleService.queryByUserName(username);
        Set<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
        info.addRoles(roleNames);
        //List<Perm> perms = permService.queryByUserName(username);
        //Set<String> permStrings = perms.stream().map(Perm::getPattern).distinct().collect(Collectors.toSet());
        List<Perm> perms = roles.stream().map(r->r.getPerms()).flatMap(List::stream).collect(Collectors.toList());
        Set<String> permStrings = perms.stream().map(Perm::getPattern).distinct().collect(Collectors.toSet());
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
        User authenticatedUser = authenService.authen(user);
        if (authenticatedUser == null) {
            throw new AccountException("用户名或密码不正确");
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
