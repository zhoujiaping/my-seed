package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.mapper.RoleMapper;
import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.model.Role;
@Service
public class RoleService {
    @Resource RoleMapper roleMapper;
    @Resource UserMapper userMapper;
    public List<Role> queryByUserName(String username) {
        return roleMapper.queryByUserName(username);
    }
}
