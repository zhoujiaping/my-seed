package cn.howso.deeplan.perm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.howso.deeplan.perm.mapper.PermMapper;
import cn.howso.deeplan.perm.model.Perm;

@Service
public class PermService {
    @Resource PermMapper permMapper;
    public List<Perm> queryByUserName(String username) {
        return permMapper.queryByUserName(username);
    }
}
