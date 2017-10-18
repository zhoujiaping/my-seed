package cn.howso.deeplan.perm.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.mapper.UserRoleMapper;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.model.UserRole;
import cn.howso.deeplan.util.Example;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource 
    private UserRoleMapper userRoleMapper;
    
    public Integer add(User user) {
        //TODO check
        return userMapper.insertSelective(user);
    }
    public List<User> query() {
        Example example = new Example();
        example.createCriteria().and("valid").equals(true);
        List<User> users = userMapper.selectByExample(example);
        return users;
    }
    public Integer delete(Integer id) {
        User user = new User();
        user.setId(id);
        user.setValid(false);
        return userMapper.updateByPrimaryKeySelective(user);
    }
    public Integer update(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
    public User get(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
    public Integer grantRoles(User currentUser, Integer userId, List<Integer> roleIdList) {
        Example e1 = new Example();
        e1.createCriteria().and("user_id").equalTo(currentUser.getId()).and("role_id").in(roleIdList);
        int count = userRoleMapper.countByExample(e1);
        Assert.isTrue(count==roleIdList.size(),"授予角色失败，当前用户必须关联授予的角色！");
        //去重
        Example example = new Example();
        example.createCriteria()
        .and("user_id").equalTo(userId)
        .and("role_id").in(roleIdList);
        userRoleMapper.deleteByExample(example);
        List<UserRole> recordList = roleIdList.stream().map(rid->{
            UserRole mid = new UserRole();
            mid.setUserId(userId);
            mid.setRoleId(rid);
            return mid;
        }).collect(Collectors.toList());
        //插入
        return userRoleMapper.batchInsert(recordList);
    }
    public User queryByName(String name) {
        Example example = new Example();
        example.createCriteria().and("name").equalTo(name);
        List<User> users = userMapper.selectByExample(example);
        if(users.isEmpty()){
            return null;
        }else if(users.size()>1){
            throw new RuntimeException("存在多个用户："+name);
        }else{
            return users.get(0);
        }
    }
    public Integer revokeRoles(User currentUser, Integer userId, List<Integer> roleIdList) {
        Example e1 = new Example();
        e1.createCriteria().and("user_id").equalTo(currentUser.getId()).and("role_id").in(roleIdList);
        int count = userRoleMapper.countByExample(e1);
        Assert.isTrue(count==roleIdList.size(),"收回角色失败，当前用户必须关联回收的角色！");
        Example example = new Example();
        example.createCriteria()
        .and("user_id").equalTo(userId)
        .and("role_id").in(roleIdList);
        return userRoleMapper.deleteByExample(example);
    }
}
