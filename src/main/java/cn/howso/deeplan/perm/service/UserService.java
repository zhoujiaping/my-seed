package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.model.UserExample;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    public Integer add(User user) {
        return userMapper.insertSelective(user);
    }
    public List<User> query() {
        UserExample example = new UserExample();
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

}
