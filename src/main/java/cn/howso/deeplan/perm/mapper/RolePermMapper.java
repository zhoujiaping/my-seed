package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.RolePerm;
import cn.howso.deeplan.util.Example;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;

@Table(name="sys_role_perm_mid")
public interface RolePermMapper extends BaseMapper<RolePerm,Example,Object>{

}
