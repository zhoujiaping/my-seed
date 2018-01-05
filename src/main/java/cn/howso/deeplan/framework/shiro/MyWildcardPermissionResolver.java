package cn.howso.deeplan.framework.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

public class MyWildcardPermissionResolver implements PermissionResolver{

    @Override
    public Permission resolvePermission(String permissionString) {
        return new MyWildcardPermission(permissionString);
    }

}
