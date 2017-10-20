package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.UserPerm;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
import cn.howso.mybatis.model.Example;

@Table(name="sys_user_perm_mid")
public interface UserPermMapper extends BaseMapper<UserPerm, Example, Object>{

}
