package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import cn.howso.deeplan.perm.mapper.PermissionMapper;
import cn.howso.deeplan.perm.model.Permission;
public class PermissionService {
    @Resource PermissionMapper permissionMapper;
    public List<Permission> queryByUserName(String username) {
        return permissionMapper.queryByUserName(username);
    }
}
