package cn.howso.deeplan.perm.mapper;

import java.util.List;

import cn.howso.deeplan.perm.dto.RoleWithPerms;
import cn.howso.deeplan.perm.model.Role;
import cn.howso.deeplan.util.Example;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
@Table(name="sys_role")
public interface RoleMapper extends BaseMapper<Role,Example,Integer>{

    List<Role> queryByUserName(String username);

    List<RoleWithPerms> queryByUserNameFetchPerms(String username);
}