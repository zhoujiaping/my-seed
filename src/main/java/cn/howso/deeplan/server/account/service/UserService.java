package cn.howso.deeplan.server.account.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.howso.deeplan.server.account.mapper.UserMapper;
import cn.howso.deeplan.server.account.model.User;
import cn.howso.deeplan.server.account.model.UserExample;
import cn.howso.deeplan.server.log.mapper.SysLogMapper;
/**
 * 用户服务
 * @ClassName UserService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @Date 2017年3月9日 下午6:15:59
 * @version 1.0.0
 */
public class UserService {
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
