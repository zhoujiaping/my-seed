package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.mapper.RoleMapper;
import cn.howso.deeplan.perm.model.Role;
import cn.howso.deeplan.util.Example;
@Service
public class RoleService {
    @Resource RoleMapper roleMapper;
    
    public List<Role> queryByUserName(String username) {
        return roleMapper.queryByUserName(username);
    }
    public Integer add(Role role) {
        return roleMapper.insertSelective(role);
    }
    public Integer delete(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }
    public Integer udpate(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }
    public List<Role> query() {
        Example example = new Example();
        return roleMapper.selectByExample(example);
    }
    public Role get(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }
}
