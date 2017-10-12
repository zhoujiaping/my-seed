package cn.howso.deeplan.perm.dto;

import java.util.List;

import cn.howso.deeplan.perm.model.Perm;
import cn.howso.deeplan.perm.model.Role;

public class RoleWithPerms extends Role{
    private List<Perm> perms;
    
    public List<Perm> getPerms() {
        return perms;
    }
    
    public void setPerms(List<Perm> perms) {
        this.perms = perms;
    }
}
