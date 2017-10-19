package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.UserPerm;
import cn.howso.deeplan.util.Example;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;

@Table(name="sys_user_perm_mid")
public interface UserPermMapper extends BaseMapper<UserPerm, Example, Object>{

}
