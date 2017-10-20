package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.mapper.PermMapper;
import cn.howso.deeplan.perm.model.Perm;
import cn.howso.mybatis.model.Example;

@Service
public class PermService {
    @Resource PermMapper permMapper;
    public List<Perm> queryByUserName(String username) {
        return permMapper.queryByUserName(username);
    }
    public List<Perm> query(Integer spaceId) {
        Example example = new Example();
        example.createCriteria().and("space_id").equalTo(spaceId);
        return permMapper.selectByExample(example);
    }
}
