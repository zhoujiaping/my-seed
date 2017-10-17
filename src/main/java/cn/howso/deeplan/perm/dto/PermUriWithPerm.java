package cn.howso.deeplan.perm.dto;

import java.util.Set;

import cn.howso.deeplan.perm.model.Perm;
import cn.howso.deeplan.perm.model.PermUri;

public class PermUriWithPerm extends PermUri{
    private Set<Perm> perms;
    
    public void setPerms(Set<Perm> perms) {
        this.perms = perms;
    }
    
    public Set<Perm> getPerms() {
        return perms;
    }
}
