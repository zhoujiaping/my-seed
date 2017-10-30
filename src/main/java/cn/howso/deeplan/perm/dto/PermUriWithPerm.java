package cn.howso.deeplan.perm.dto;

import java.util.List;

import cn.howso.deeplan.perm.model.Perm;
import cn.howso.deeplan.perm.model.PermUri;

public class PermUriWithPerm extends PermUri{
    private List<Perm> perms;
    
    public void setPerms(List<Perm> perms) {
        this.perms = perms;
    }
    
    public List<Perm> getPerms() {
        return perms;
    }
}
