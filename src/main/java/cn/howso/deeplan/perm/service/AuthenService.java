package cn.howso.deeplan.perm.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.howso.deeplan.perm.cache.RedisCache;
import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.util.Example;
/**
 * 用户服务
 * @ClassName UserService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @Date 2017年3月9日 下午6:15:59
 * @version 1.0.0
 */
@Service
public class AuthenService {
    @Resource 
    private UserMapper userMapper;
    @Resource(name="authenCache")
    private RedisCache cache;
    public User authen(User user){
        Example example = new Example();
        example.createCriteria()
        .and("name").equalTo(user.getName())
        .and("password").equalTo(user.getPassword());
        List<User> users = userMapper.selectByExample(example);
        Assert.isTrue(users.size()<=1,"查询到多个用户");
        if(users.size()==1){
            return users.get(0);
        }
        return null;
    }
    public void removeCache(String username) {
        Set<Object> keys = cache.keys();
        for(Object key:keys){
            String usernameInCache = (String) key;
            if(username.equals(usernameInCache)){
                cache.remove(key);
            }
        }
    }
}
