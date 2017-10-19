package cn.howso.deeplan.perm.model;


public class RolePerm {
    private Integer roleId;
    private Integer permId;
    
    public Integer getPermId() {
        return permId;
    }
    public void setPermId(Integer permId) {
        this.permId = permId;
    }
    
    public Integer getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
