package cn.howso.deeplan.perm.dto;

import cn.howso.deeplan.perm.model.Perm;
import cn.howso.deeplan.perm.model.UriPerm;

public class UriPermWithPerm extends UriPerm{
    private Perm perm;
    
    public Perm getPerm() {
        return perm;
    }
    
    public void setPerm(Perm perm) {
        this.perm = perm;
    }
}
