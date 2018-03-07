package cn.howso.deeplan.perm.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cn.howso.deeplan.perm.dao.UserDao;
import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.mapper.UserPermMapper;
import cn.howso.deeplan.perm.mapper.UserRoleMapper;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.model.UserPerm;
import cn.howso.deeplan.perm.model.UserRole;
import cn.howso.mybatis.model.Example;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource 
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserPermMapper userPermMapper;
    @Resource
    private AuthorService authorService;
    @Resource
    private UserDao userDao;
    
    public Integer add(User user, Integer spaceId) {
        user.setSpaceId(spaceId);
        Assert.isTrue(!StringUtils.isEmpty(user.getName()),"用户名不能为空");
        Assert.isTrue(!StringUtils.isEmpty(user.getPassword()),"密码不能为空");
        return userMapper.insertSelective(user);
    }
    @Cacheable(value="userCache",key="")
    public List<User> query(Integer spaceId) {
        Example example = new Example();
        example.createCriteria()
        .and("valid").equalTo(true)
        .and("space_id").equalTo(spaceId);
        List<User> users = userMapper.selectByExample(example);
        User user = userDao.getById(-1L);
        System.out.println(user);
        return users;
    }
    @CacheEvict(value="accountCache",allEntries=true,beforeInvocation=true)
    public Integer delete(Integer id,Integer spaceId) {
        User user = new User();
        user.setId(id);
        user.setValid(false);
        Example example = new Example();
        example.createCriteria().and("id").equalTo(id)
        .and("space_id").equalTo(spaceId);
        return userMapper.updateByExampleSelective(user, example );
    }
    @CacheEvict(value="authenCache",key="#user.getName()",beforeInvocation=true)
    //@Cacheable(value="accountCache",key="#userName.concat(#password)") 
    public Integer update(User user,Integer spaceId) {
        Example example = new Example();
        example.createCriteria().and("id").equalTo(user.getId())
        .and("space_id").equalTo(spaceId);
        return userMapper.updateByExampleSelective(user, example );
    }
    public User get(Integer id, Integer spaceId) {
        User user = userMapper.selectByPrimaryKey(id);
        if(Objects.equals(spaceId, user.getSpaceId())){
            return user;
        }
        return null;
    }
    @CacheEvict(value="authorCache",key="#user.getName()",beforeInvocation=true)
    public Integer grantRoles(User user, List<Integer> roleIdList) {
        //去重
        revokeRoles(user, roleIdList);
        List<UserRole> userRoleList = roleIdList.stream().map(rid->{
            UserRole mid = new UserRole();
            mid.setUserId(user.getId());
            mid.setRoleId(rid);
            return mid;
        }).collect(Collectors.toList());
        //插入
        return userRoleMapper.batchInsert(userRoleList);
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
    @CacheEvict(value="authorCache",key="#user.getName()",beforeInvocation=true)
    public Integer revokeRoles(User user, List<Integer> roleIdList) {
        Example example = new Example();
        example.createCriteria()
        .and("user_id").equalTo(user.getId())
        .and("role_id").in(roleIdList);
        return userRoleMapper.deleteByExample(example);
    }
    @CacheEvict(value="authorCache",key="#user.getName()",beforeInvocation=true)
    public Integer grantPerms(User user, Integer spaceId, List<Integer> permIdList) {
        //去重
        revokePerms(user,spaceId,permIdList);
        List<UserPerm> recordList = permIdList.stream().map(permId->{
            UserPerm mid = new UserPerm();
            mid.setUserId(user.getId());
            mid.setPermId(permId);
            return mid;
        }).collect(Collectors.toList());
        //插入
        return userPermMapper.batchInsertSelective(recordList);
    }
    @CacheEvict(value="authorCache",key="#user.getName()",beforeInvocation=true)
    public Integer revokePerms(User user, Integer _permSpaceId, List<Integer> permIdList) {
        Example example = new Example();
        example.createCriteria().and("user_id").equalTo(user.getId()).and("perm_id").in(permIdList);
        return userPermMapper.deleteByExample(example);
    }
}
