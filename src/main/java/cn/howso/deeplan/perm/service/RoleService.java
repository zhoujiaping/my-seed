package cn.howso.deeplan.perm.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.mapper.RoleMapper;
import cn.howso.deeplan.perm.mapper.RolePermMapper;
import cn.howso.deeplan.perm.model.Role;
import cn.howso.deeplan.perm.model.RolePerm;
import cn.howso.deeplan.util.Example;
@Service
public class RoleService {
    @Resource RoleMapper roleMapper;
    @Resource RolePermMapper rolePermMapper;
    public List<Role> queryByUserName(String username) {
        return roleMapper.queryByUserName(username);
    }
    public Integer add(Role role,Integer spaceId) {
        role.setSpaceId(spaceId);
        return roleMapper.insertSelective(role);
    }
    public Integer delete(Integer id,Integer spaceId) {
        Example example = new Example();
        example.createCriteria().and("id").equalTo(id).and("space_id").equalTo(spaceId);
        return roleMapper.deleteByExample(example);
    }
    public Integer udpate(Role role,Integer spaceId) {
        Example example = new Example();
        example.createCriteria().and("id").equalTo(role.getId()).and("space_id").equalTo(spaceId);
        return roleMapper.updateByExampleSelective(role,example);
    }
    public List<Role> query(Integer spaceId) {
        Example example = new Example();
        example.createCriteria().and("space_id").equalTo(spaceId);
        return roleMapper.selectByExample(example);
    }
    public Role get(Integer id,Integer spaceId) {
        Role role = roleMapper.selectByPrimaryKey(id);
        if(spaceId==role.getSpaceId()){
            return role;
        }
        return null;
    }
    public Integer grantPerms(Integer roleId, List<Integer> permIdList) {
        //去重
        revokePerms(roleId,permIdList);
        List<RolePerm> recordList = permIdList.stream().map(permId->{
            RolePerm mid = new RolePerm();
            mid.setRoleId(roleId);
            mid.setPermId(permId);
            return mid;
        }).collect(Collectors.toList());
        //插入
        return rolePermMapper.batchInsertSelective(recordList);
    }
    public Integer revokePerms(Integer roleId, List<Integer> permIdList) {
        Example example = new Example();
        example.createCriteria().and("role_id").equalTo(roleId).and("perm_id").in(permIdList);
        return rolePermMapper.deleteByExample(example);
    }
}
