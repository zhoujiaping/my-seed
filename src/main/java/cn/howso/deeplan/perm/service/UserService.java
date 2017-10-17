package cn.howso.deeplan.perm.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.mapper.UserRoleMapper;
import cn.howso.deeplan.perm.model.Example;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.model.UserRole;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource 
    private UserRoleMapper userRoleMapper;
    
    public Integer add(User user) {
        return userMapper.insertSelective(user);
    }
    public List<User> query() {
        Example example = new Example();
        List<User> users = userMapper.selectByExample(example);
        users.forEach(u->u.setPassword(null));
        return users;
    }
    public Integer delete(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }
    public Integer udpate(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
    public User get(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
    public Integer grantRoles(User currentUser, Integer userId, List<Integer> roleIdList) {
        
        //TODO 校验该用户是否有这些权限
        //去重
        Example example = new Example();
        example.createCriteria()
        .and("user_id").equalTo(userId)
        .and("role_id").in(roleIdList);
        userRoleMapper.deleteByExample(example );
        List<UserRole> recordList = roleIdList.stream().map(rid->{
            UserRole mid = new UserRole();
            mid.setUserId(userId);
            mid.setRoleId(rid);
            return mid;
        }).collect(Collectors.toList());
        //插入
        return userRoleMapper.batchInsert(recordList);
    }

}
