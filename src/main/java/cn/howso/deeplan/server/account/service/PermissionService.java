package cn.howso.deeplan.server.account.service;

import java.util.List;

import javax.annotation.Resource;

import cn.howso.deeplan.server.account.mapper.PermissionMapper;
import cn.howso.deeplan.server.account.model.Permission;
public class PermissionService {
    @Resource PermissionMapper permissionMapper;
    public List<Permission> queryByUserName(String username) {
        return permissionMapper.queryByUserName(username);
    }
}
