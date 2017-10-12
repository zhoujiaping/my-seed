package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.model.UserExample;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
@Table(name="sys_user")
public interface UserMapper extends BaseMapper<User,UserExample,Integer>{

    //java.lang.IllegalArgumentException: Result Maps collection does not contain value for cn.howso.mybatis.mapper.BaseMapper.BaseResultMap

}