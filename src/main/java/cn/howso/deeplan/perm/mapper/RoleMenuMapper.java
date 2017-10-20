package cn.howso.deeplan.perm.mapper;

import cn.howso.deeplan.perm.model.RoleMenu;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
import cn.howso.mybatis.model.Example;

@Table(name="sys_role_menu_mid")
public interface RoleMenuMapper extends BaseMapper<RoleMenu, Example, Object>{

}
