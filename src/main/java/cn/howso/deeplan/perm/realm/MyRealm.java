package cn.howso.deeplan.perm.realm;

import java.util.Set;

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
    /**
     * 获得授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roleNames = authorService.queryRoleNames(username);
        info.addRoles(roleNames);
        Set<String> permStrings = authorService.queryPermStrings(username);
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
