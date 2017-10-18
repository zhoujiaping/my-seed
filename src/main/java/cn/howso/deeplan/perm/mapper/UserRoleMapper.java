package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.UserRole;
import cn.howso.deeplan.util.Example;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
@Table(name="sys_user_role_mid")
public interface UserRoleMapper extends BaseMapper<UserRole,Example, Object>{
}