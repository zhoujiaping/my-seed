package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.howso.deeplan.perm.mapper.UserMapper;
import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.model.UserExample;
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
    @Resource UserMapper userMapper;
    public User authen(User user){
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(user.getName()).andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(example);
        Assert.isTrue(users.size()<=1,"查询到多个用户");
        if(users.size()==1){
            return users.get(0);
        }
        return null;
    }
}
